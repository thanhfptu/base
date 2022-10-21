package vn.edu.fpt.capstone.api.services.importhistory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vn.edu.fpt.capstone.api.configs.kafka.producers.ImportProducerConfig;
import vn.edu.fpt.capstone.api.constants.ImportHistoryStatus;
import vn.edu.fpt.capstone.api.constants.ImportHistoryType;
import vn.edu.fpt.capstone.api.constants.SearchOperation;
import vn.edu.fpt.capstone.api.entities.ImportHistory;
import vn.edu.fpt.capstone.api.messages.ImportMessage;
import vn.edu.fpt.capstone.api.models.specification.BaseSpecification;
import vn.edu.fpt.capstone.api.models.specification.SearchCriteria;
import vn.edu.fpt.capstone.api.repositories.ImportHistoryRepository;
import vn.edu.fpt.capstone.api.requests.ImportHistoryRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.ImportHistoryResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.utils.DateUtils;
import vn.edu.fpt.capstone.api.utils.FileUtils;
import vn.edu.fpt.capstone.api.utils.JSONUtils;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service(ImportHistoryService.SERVICE_NAME)
public class ImportHistoryServiceImpl implements ImportHistoryService {

    private final ImportHistoryRepository importHistoryRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public ImportHistoryServiceImpl(ImportHistoryRepository importHistoryRepository,
                                    @Qualifier(ImportProducerConfig.KAFKA_TEMPLATE) KafkaTemplate<String, String> kafkaTemplate) {
        this.importHistoryRepository = importHistoryRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public PageResponse<ImportHistoryResponse> list(Pageable pageable,
                                                    String fileName,
                                                    List<Integer> types,
                                                    List<Integer> statuses,
                                                    Date createdAtFrom,
                                                    Date createdAtTo) {
        BaseSpecification<ImportHistory> specification = new BaseSpecification<>();

        if (StringUtils.hasText(fileName)) {
            specification.add(new SearchCriteria("fileName", fileName, SearchOperation.MATCH));
        }

        if (!CollectionUtils.isEmpty(types)) {
            specification.add(new SearchCriteria("type", types, SearchOperation.IN));
        }

        if (!CollectionUtils.isEmpty(statuses)) {
            specification.add(new SearchCriteria("status", statuses, SearchOperation.IN));
        }

        if (Objects.nonNull(createdAtFrom)) {
            specification.add(new SearchCriteria("createdAt", DateUtils.at(DateUtils.toLocalDate(createdAtFrom), LocalTime.MIN), SearchOperation.GREATER_THAN_EQUAL));
        }

        if (Objects.nonNull(createdAtTo)) {
            specification.add(new SearchCriteria("createdAt", DateUtils.at(DateUtils.toLocalDate(createdAtTo), LocalTime.MAX), SearchOperation.LESS_THAN_EQUAL));
        }

        Page<ImportHistory> page = importHistoryRepository.findAll(specification, pageable);

        List<ImportHistory> content = CollectionUtils.isEmpty(page.getContent())
                ? Collections.emptyList() : page.getContent();

        List<ImportHistoryResponse> data = content.stream()
                .map(ImportHistoryResponse::of)
                .toList();

        return PageResponse.success(data, page.getNumber(), page.getSize(), page.getTotalElements(), (long) page.getTotalPages());
    }

    @Override
    public BaseResponse<ImportHistoryResponse> create(ImportHistoryRequest request, Long operatorId) {
        ImportHistory importHistory = importHistoryRepository.save(ImportHistory.of(request, operatorId));
        try {

            if (!FileUtils.hasExcelFormat(request.getFile()) || FileUtils.isEmptyName(request.getFile()))
                throw new IOException(String.format("Tên file không hợp lệ!"));

            String fileName = FileUtils.getFileName(request.getFile());
            importHistory.setType(ImportHistoryType.of(request.getType()));

            String date = DateUtils.format(DateUtils.now(), DateUtils.DATETIME_FORMAT_CUSTOM);
            fileName = String.format("%s_%s_%s.xls", fileName, operatorId, date);
            FileUtils.convertToFile(request.getFile(), fileName);

            importHistory.setFileName(fileName);
            importHistory.setSourceURL(FileUtils.uploadFile(fileName, "original"));
            importHistory.setStatus(ImportHistoryStatus.PENDING);
            importHistory = importHistoryRepository.save(importHistory);
            kafkaTemplate.send(ImportProducerConfig.TOPIC, JSONUtils.toJSON(ImportMessage.of(importHistory)));
            return BaseResponse.success(ImportHistoryResponse.of(importHistory));
        } catch (Exception e) {
            importHistory.setStatus(ImportHistoryStatus.FAILED);
            importHistory.setMessage(e.getMessage());
            importHistory.setModifiedAt(DateUtils.now());
            importHistory = importHistoryRepository.save(importHistory);
            return BaseResponse.error(importHistory.getMessage());
        }
    }

}
