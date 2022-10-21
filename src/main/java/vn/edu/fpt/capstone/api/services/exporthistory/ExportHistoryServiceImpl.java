package vn.edu.fpt.capstone.api.services.exporthistory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vn.edu.fpt.capstone.api.configs.kafka.producers.ExportProducerConfig;
import vn.edu.fpt.capstone.api.constants.ExportHistoryType;
import vn.edu.fpt.capstone.api.constants.SearchOperation;
import vn.edu.fpt.capstone.api.entities.ExportHistory;
import vn.edu.fpt.capstone.api.messages.ExportCVMessage;
import vn.edu.fpt.capstone.api.messages.ExportMessage;
import vn.edu.fpt.capstone.api.models.specification.BaseSpecification;
import vn.edu.fpt.capstone.api.models.specification.SearchCriteria;
import vn.edu.fpt.capstone.api.repositories.ExportHistoryRepository;
import vn.edu.fpt.capstone.api.requests.ExportHistoryRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.ExportHistoryResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.utils.DateUtils;
import vn.edu.fpt.capstone.api.utils.JSONUtils;

import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service(ExportHistoryService.SERVICE_NAME)
public class ExportHistoryServiceImpl implements ExportHistoryService{

    private final ExportHistoryRepository exportHistoryRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final List<ExportHistoryType> TYPE_CV = List.of(ExportHistoryType.CV_COMPANY, ExportHistoryType.CV_SEMESTER);
    @Autowired
    public ExportHistoryServiceImpl(ExportHistoryRepository exportHistoryRepository,
                                    @Qualifier(ExportProducerConfig.KAFKA_TEMPLATE) KafkaTemplate<String, String> kafkaTemplate) {
        this.exportHistoryRepository = exportHistoryRepository;
        this.kafkaTemplate = kafkaTemplate;
    }
    @Override
    public PageResponse<ExportHistoryResponse> list(Pageable pageable,
                                                    String fileName,
                                                    List<Integer> types,
                                                    List<Integer> statuses,
                                                    Date createdAtFrom,
                                                    Date createdAtTo) {
        BaseSpecification<ExportHistory> specification = new BaseSpecification<>();

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

        Page<ExportHistory> page = exportHistoryRepository.findAll(specification, pageable);

        List<ExportHistory> content = CollectionUtils.isEmpty(page.getContent())
                ? Collections.emptyList() : page.getContent();

        List<ExportHistoryResponse> data = content.stream()
                .map(ExportHistoryResponse::of)
                .toList();

        return PageResponse.success(data, page.getNumber(), page.getSize(), page.getTotalElements(), (long) page.getTotalPages());
    }

    @Override
    public BaseResponse<ExportHistoryResponse> create(ExportHistoryRequest request, Long operatorId) {
            ExportHistory exportHistory = exportHistoryRepository.save(ExportHistory.of(request, operatorId));

            if (TYPE_CV.contains(ExportHistoryType.of(request.getType()))) {
                kafkaTemplate.send(ExportProducerConfig.TOPIC, JSONUtils.toJSON(new ExportCVMessage(exportHistory, request.getCompanyId())));
            } else {
                kafkaTemplate.send(ExportProducerConfig.TOPIC, JSONUtils.toJSON(ExportMessage.of(exportHistory)));
            }

            return BaseResponse.success(ExportHistoryResponse.of(exportHistory));
    }
}
