package vn.edu.fpt.capstone.api.services.role;

import org.springframework.data.domain.Pageable;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.RoleResponse;

import java.util.List;

public interface RoleService {

    String SERVICE_NAME = "RoleService";

    PageResponse<RoleResponse> list(Pageable pageable,
                                    String code,
                                    String name);

    BaseResponse<List<RoleResponse>> getUserRoles(Long id);

}
