package vn.edu.fpt.capstone.api.services.importhistory;

import org.springframework.data.domain.Pageable;
import vn.edu.fpt.capstone.api.requests.ImportHistoryRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.ImportHistoryResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;

import java.util.Date;
import java.util.List;


public interface ImportHistoryService {

    String SERVICE_NAME = "ImportHistoryService";

    PageResponse<ImportHistoryResponse> list(Pageable pageable,
                                             String fileName,
                                             List<Integer> types,
                                             List<Integer> statuses,
                                             Date createdAtFrom,
                                             Date createdAtTo);

    BaseResponse<ImportHistoryResponse> create(ImportHistoryRequest request, Long operatorId);

}
