package vn.edu.fpt.capstone.api.services.campus;

import org.springframework.data.domain.Pageable;
import vn.edu.fpt.capstone.api.requests.IdsRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.CampusResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.entities.Campus;
import vn.edu.fpt.capstone.api.entities.Region;
import vn.edu.fpt.capstone.api.requests.CampusRequest;

import java.util.List;

public interface CampusService {

    String SERVICE_NAME = "CampusService";

    PageResponse<CampusResponse> list(Pageable pageable,
                                      String name,
                                      String address,
                                      Long regionId,
                                      String phoneNumber,
                                      String email,
                                      Integer status);

    BaseResponse<CampusResponse> get(Long id);

    BaseResponse<CampusResponse> create(CampusRequest request, Region region, Long operatorId);

    BaseResponse<CampusResponse> update(CampusRequest request, Region region, Long operatorId);

    BaseResponse<CampusResponse> save(CampusRequest request, Long operatorId);

    BaseResponse<CampusResponse> response(Campus campus, Region region);

    BaseResponse<List<CampusResponse>> disable(IdsRequest request, Long operatorId);

}
