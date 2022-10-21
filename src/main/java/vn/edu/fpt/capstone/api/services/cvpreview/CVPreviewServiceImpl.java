package vn.edu.fpt.capstone.api.services.cvpreview;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.api.entities.CVPreview;
import vn.edu.fpt.capstone.api.repositories.CVPreviewRepository;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.CVPreviewResponse;

import java.util.List;

@RequiredArgsConstructor
@Service(CVPreviewService.SERVICE_NAME)
public class CVPreviewServiceImpl implements CVPreviewService{

    private final CVPreviewRepository cvPreviewRepository;

    @Override
    public BaseResponse<List<CVPreviewResponse>> getByCVId(Long cvId) {
        List<CVPreview> cvPreviews = cvPreviewRepository.findByCvId(cvId);
        List<CVPreviewResponse> responses = cvPreviews.stream()
                .map(CVPreviewResponse::of)
                .toList();
        return BaseResponse.success(responses);
    }
}
