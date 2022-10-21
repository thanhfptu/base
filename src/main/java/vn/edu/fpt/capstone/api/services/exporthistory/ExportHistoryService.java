package vn.edu.fpt.capstone.api.services.exporthistory;

import org.springframework.data.domain.Pageable;
import vn.edu.fpt.capstone.api.requests.ExportHistoryRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.ExportHistoryResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;

import java.util.Date;
import java.util.List;

public interface ExportHistoryService {
    String SERVICE_NAME = "ExportHistoryService";

    PageResponse<ExportHistoryResponse> list(Pageable pageable,
                                             String fileName,
                                             List<Integer> types,
                                             List<Integer> statuses,
                                             Date createdAtFrom,
                                             Date createdAtTo);

    BaseResponse<ExportHistoryResponse> create(ExportHistoryRequest request, Long operatorId);
}
