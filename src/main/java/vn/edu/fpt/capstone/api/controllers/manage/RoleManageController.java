package vn.edu.fpt.capstone.api.controllers.manage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.RoleResponse;
import vn.edu.fpt.capstone.api.services.role.RoleService;

@RestController
@RequiredArgsConstructor
@RequestMapping(RoleManageController.PATH)
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Role", description = "Quản lý Role")
public class RoleManageController {

    public static final String PATH = AppConfig.MANAGE_PATH + AppConfig.V1_PATH + "/roles";

    private final RoleService roleService;

    @GetMapping
    @Operation(summary = "Lấy danh sách Role")
    public ResponseEntity<PageResponse<RoleResponse>> list(@RequestParam Integer currentPage,
                                                           @RequestParam Integer pageSize,
                                                           @RequestParam(required = false) String code,
                                                           @RequestParam(required = false) String name) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        return AppResponse.success(roleService.list(pageable, code, name));
    }

}
