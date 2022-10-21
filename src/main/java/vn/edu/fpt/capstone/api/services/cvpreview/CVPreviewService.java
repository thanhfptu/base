package vn.edu.fpt.capstone.api.services.cvpreview;

import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.CVPreviewResponse;

import java.util.List;

public interface CVPreviewService {
    String SERVICE_NAME = "CVPreviewService";

    BaseResponse<List<CVPreviewResponse>> getByCVId(Long cvId);
}
