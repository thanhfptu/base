package vn.edu.fpt.capstone.api.services.midtermevaluation;

import org.springframework.data.domain.Pageable;
import vn.edu.fpt.capstone.api.entities.*;
import vn.edu.fpt.capstone.api.entities.dto.MidtermEvaluationDTO;
import vn.edu.fpt.capstone.api.requests.MidtermEvaluationRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.MidtermEvaluationResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;

import java.util.Date;

public interface MidtermEvaluationService {
    String SERVICE_NAME = "MidtermEvaluationService";

    PageResponse<MidtermEvaluationDTO> list(Pageable pageable,
                                            String studentName,
                                            String rollNumber,
                                            Long companyId,
                                            Long semesterId,
                                            String companyName,
                                            Date startDate,
                                            Date endDate,
                                            String staffCode,
                                            Integer comment,
                                            Long companyContactId,
                                            String contactName,
                                            Integer status);

    BaseResponse<MidtermEvaluationResponse> get(Long id);

    BaseResponse<MidtermEvaluationResponse> save(MidtermEvaluationRequest request);

    BaseResponse<MidtermEvaluationResponse> create(MidtermEvaluationRequest request);

    BaseResponse<MidtermEvaluationResponse> update(MidtermEvaluationRequest request);

}
