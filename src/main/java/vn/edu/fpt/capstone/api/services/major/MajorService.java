package vn.edu.fpt.capstone.api.services.major;

import org.springframework.data.domain.Pageable;
import vn.edu.fpt.capstone.api.entities.Major;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.MajorResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.requests.MajorRequest;

public interface MajorService {

    String SERVICE_NAME = "MajorService";

    PageResponse<MajorResponse> list(Pageable pageable,
                                     String code,
                                     String name,
                                     Integer level,
                                     Long parentId,
                                     Boolean enabled);

    BaseResponse<MajorResponse> get(Long id);

    BaseResponse<MajorResponse> save(MajorRequest request, Long operatorId);

    BaseResponse<MajorResponse> create(MajorRequest request, Major parentMajor, Long operatorId);

    BaseResponse<MajorResponse> update(MajorRequest request, Major parentMajor, Long operatorId);

    BaseResponse<MajorResponse> response(Major major, Major parentMajor);

}
