package vn.edu.fpt.capstone.api.services.userCV;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import vn.edu.fpt.capstone.api.entities.CVPreview;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.entities.UserCV;
import vn.edu.fpt.capstone.api.entities.dto.AppliedJobDTO;
import vn.edu.fpt.capstone.api.entities.dto.ManageCVDTO;
import vn.edu.fpt.capstone.api.requests.UserCVRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.UserCVResponse;

import java.util.List;

public interface UserCVService {
    String SERVICE_NAME = "UserCVService";

    PageResponse<ManageCVDTO> list(Pageable pageable,
                                   Long companyId,
                                   Long jobId,
                                   String email,
                                   Integer status);

    BaseResponse<UserCVResponse> get(Long id);

    BaseResponse<List<UserCVResponse>> getByStudentId(Long studentId);

    BaseResponse<UserCVResponse> response(UserCV userCV, List<CVPreview> cvPreview, List<AppliedJobDTO> appliedJobs);

    BaseResponse<UserCVResponse> upload(MultipartFile cv, User operator);

    BaseResponse<UserCVResponse> update(MultipartFile cv, User operator, Long id);

    BaseResponse<List<UserCVResponse>> changeStatus(List<Long> ids, Integer status, Long operatorId);

    BaseResponse<UserCVResponse> updateApprove(UserCVRequest request, Long operatorId);
}
