package vn.edu.fpt.capstone.api.services.role;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vn.edu.fpt.capstone.api.constants.SearchOperation;
import vn.edu.fpt.capstone.api.entities.Role;
import vn.edu.fpt.capstone.api.entities.UserRole;
import vn.edu.fpt.capstone.api.models.specification.BaseSpecification;
import vn.edu.fpt.capstone.api.models.specification.SearchCriteria;
import vn.edu.fpt.capstone.api.repositories.RoleRepository;
import vn.edu.fpt.capstone.api.repositories.UserRoleRepository;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.RoleResponse;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service(RoleService.SERVICE_NAME)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public PageResponse<RoleResponse> list(Pageable pageable, String code, String name) {
        BaseSpecification<Role> specification = new BaseSpecification<>();

        if (StringUtils.hasText(code)) {
            specification.add(new SearchCriteria("code", code, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(name)) {
            specification.add(new SearchCriteria("name", name, SearchOperation.MATCH));
        }

        Page<Role> page = roleRepository.findAll(specification, pageable);

        List<Role> content = CollectionUtils.isEmpty(page.getContent()) ? Collections.emptyList() : page.getContent();

        List<RoleResponse> data = content.stream()
                .map(RoleResponse::of)
                .toList();

        return PageResponse.success(data, page.getNumber(), page.getSize(), page.getTotalElements(), (long) page.getTotalPages());
    }

    @Override
    public BaseResponse<List<RoleResponse>> getUserRoles(Long id) {
        List<UserRole> userRoles = userRoleRepository.findByUserId(id);

        if (CollectionUtils.isEmpty(userRoles)) {
            return BaseResponse.success(Collections.emptyList());
        }

        List<Long> roleIds = userRoles.stream()
                .map(UserRole::getRoleId)
                .toList();

        List<Role> roles = roleRepository.findByIdIn(roleIds);

        if (CollectionUtils.isEmpty(roles)) {
            return BaseResponse.success(Collections.emptyList());
        }

        if (roleIds.size() != roles.size()) {
            return BaseResponse.error("Role không hợp lệ!");
        }

        List<RoleResponse> roleResponses = roles.stream().map(RoleResponse::of).toList();

        return BaseResponse.success(roleResponses);
    }

}
