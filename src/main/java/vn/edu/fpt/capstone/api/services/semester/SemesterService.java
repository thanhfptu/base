package vn.edu.fpt.capstone.api.services.semester;

import org.springframework.data.domain.Pageable;
import vn.edu.fpt.capstone.api.requests.IdsRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.SemesterResponse;
import vn.edu.fpt.capstone.api.requests.SemesterRequest;
import vn.edu.fpt.capstone.api.responses.PageResponse;

import java.util.Date;
import java.util.List;

public interface SemesterService {

    String SERVICE_NAME = "SemesterService";

    PageResponse<SemesterResponse> list(Pageable pageable,
                                        String name,
                                        String code,
                                        Integer year,
                                        Date startDate,
                                        Date endDate,
                                        Boolean isActive);

    BaseResponse<SemesterResponse> get(Long id);

    BaseResponse<SemesterResponse> save(SemesterRequest request, Long operatorId);

    BaseResponse<SemesterResponse> create(SemesterRequest request, Long operatorId);

    BaseResponse<SemesterResponse> update(SemesterRequest request, Long operatorId);

    BaseResponse<List<SemesterResponse>> disable(IdsRequest request, Long operatorId);

}
