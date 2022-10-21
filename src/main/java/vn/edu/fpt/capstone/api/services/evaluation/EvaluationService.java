package vn.edu.fpt.capstone.api.services.evaluation;

import vn.edu.fpt.capstone.api.entities.dto.EvaluationDTO;
import vn.edu.fpt.capstone.api.responses.BaseResponse;

import java.util.List;

public interface EvaluationService {
    String SERVICE_NAME = "EvaluationService";

    BaseResponse<List<EvaluationDTO>> getByContactId(Long contactId);
}
