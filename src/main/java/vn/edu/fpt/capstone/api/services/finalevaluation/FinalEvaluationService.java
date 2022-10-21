package vn.edu.fpt.capstone.api.services.finalevaluation;

import org.springframework.data.domain.Pageable;
import vn.edu.fpt.capstone.api.entities.CompanyContact;
import vn.edu.fpt.capstone.api.entities.FinalEvaluation;
import vn.edu.fpt.capstone.api.entities.Semester;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.entities.dto.FinalEvaluationDTO;
import vn.edu.fpt.capstone.api.requests.FinalEvaluationRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.FinalEvaluationResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;

import java.util.Date;

public interface FinalEvaluationService {
    String SERVICE_NAME = "FinalEvaluationService";

    PageResponse<FinalEvaluationDTO> list(Pageable pageable,
                                          String studentName,
                                          String rollNumber,
                                          Long companyId,
                                          Long semesterId,
                                          String companyName,
                                          Date startDate,
                                          Date endDate,
                                          String staffCode,
                                          Long companyContactId,
                                          String contactName,
                                          Integer status);

    BaseResponse<FinalEvaluationResponse> get(Long id);

    BaseResponse<FinalEvaluationResponse> save(FinalEvaluationRequest request);

    BaseResponse<FinalEvaluationResponse> create(FinalEvaluationRequest request);

    BaseResponse<FinalEvaluationResponse> update(FinalEvaluationRequest request);

}
