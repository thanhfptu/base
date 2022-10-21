package vn.edu.fpt.capstone.api.services.region;

import org.springframework.data.domain.Pageable;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.entities.Region;
import vn.edu.fpt.capstone.api.requests.RegionRequest;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.RegionResponse;

public interface RegionService {

    String SERVICE_NAME = "RegionService";

    PageResponse<RegionResponse> list(Pageable pageable,
                                      Integer level,
                                      String name,
                                      Long parentId);

    BaseResponse<RegionResponse> get(Long id);

    BaseResponse<RegionResponse> create(RegionRequest request, Region parentRegion, Long operatorId);

    BaseResponse<RegionResponse> update(RegionRequest request, Region parentRegion, Long operatorId);

    BaseResponse<RegionResponse> save(RegionRequest request, Long operatorId);

    BaseResponse<RegionResponse> response(Region region, Region parentRegion);

}
