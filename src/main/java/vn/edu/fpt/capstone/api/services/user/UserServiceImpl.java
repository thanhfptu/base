package vn.edu.fpt.capstone.api.services.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vn.edu.fpt.capstone.api.constants.Gender;
import vn.edu.fpt.capstone.api.constants.SearchOperation;
import vn.edu.fpt.capstone.api.constants.StudentOJTStatus;
import vn.edu.fpt.capstone.api.constants.StudentStatus;
import vn.edu.fpt.capstone.api.entities.*;
import vn.edu.fpt.capstone.api.models.AppUserDetails;
import vn.edu.fpt.capstone.api.models.specification.BaseSpecification;
import vn.edu.fpt.capstone.api.models.specification.SearchCriteria;
import vn.edu.fpt.capstone.api.repositories.*;
import vn.edu.fpt.capstone.api.requests.UserRequest;
import vn.edu.fpt.capstone.api.responses.*;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service(UserService.SERVICE_NAME)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final MajorRepository majorRepository;
    private final CampusRepository campusRepository;
    private final UserRoleRepository userRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEduEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Email %s not found!", username)));

        List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());

        if (CollectionUtils.isEmpty(userRoles)) return new AppUserDetails(user, Collections.emptyList());

        List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).toList();

        List<Role> roles = roleRepository.findByIdIn(roleIds);

        if (CollectionUtils.isEmpty(roles)) return new AppUserDetails(user, Collections.emptyList());

        return new AppUserDetails(user, roles);
    }

    @Override
    public PageResponse<UserResponse> list(Pageable pageable,
                                           String fullName,
                                           String rollNumber,
                                           Integer gender,
                                           Integer status,
                                           List<Long> majorIds,
                                           Long campusId,
                                           String eduEmail,
                                           String personalEmail,
                                           String phoneNumber,
                                           String address,
                                           Integer ojtStatus) {
        BaseSpecification<User> specification = new BaseSpecification<>();

        if (StringUtils.hasText(fullName)) {
            specification.add(new SearchCriteria("fullName", fullName, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(rollNumber)) {
            specification.add(new SearchCriteria("rollNumber", rollNumber, SearchOperation.MATCH));
        }

        if (Objects.nonNull(gender)) {
            specification.add(new SearchCriteria("gender", gender, SearchOperation.MATCH));
        }

        if (Objects.nonNull(status)) {
            specification.add(new SearchCriteria("status", status, SearchOperation.MATCH));
        }

        if (!CollectionUtils.isEmpty(majorIds)) {
            specification.add(new SearchCriteria("majorId", majorIds, SearchOperation.IN));
        }

        if (Objects.nonNull(campusId)) {
            specification.add(new SearchCriteria("campusId", campusId, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(eduEmail)) {
            specification.add(new SearchCriteria("eduEmail", eduEmail, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(personalEmail)) {
            specification.add(new SearchCriteria("personalEmail", personalEmail, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(phoneNumber)) {
            specification.add(new SearchCriteria("phoneNumber", phoneNumber, SearchOperation.MATCH));
        }

        if (StringUtils.hasText(address)) {
            specification.add(new SearchCriteria("address", address, SearchOperation.MATCH));
        }

        if (Objects.nonNull(ojtStatus)) {
            specification.add(new SearchCriteria("ojtStatus", ojtStatus, SearchOperation.EQUAL));
        }

        Page<User> page = userRepository.findAll(specification, pageable);
        List<User> content = CollectionUtils.isEmpty(page.getContent()) ? Collections.emptyList() : page.getContent();

        List<UserResponse> data = content.stream()
                .map(UserResponse::of)
                .toList();

        return PageResponse.success(data, page.getNumber(), page.getSize(), page.getTotalElements(), (long) page.getTotalPages());
    }

    @Override
    public BaseResponse<UserResponse> get(Long userId) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy User với id %s!", userId)));

            Long majorId = user.getMajorId();

            Major major = Objects.isNull(majorId)
                    ? null
                    : majorRepository.findById(majorId).orElse(null);

            Long campusId = user.getCampusId();

            Campus campus = campusRepository.findById(campusId).orElse(null);

            List<UserRole> userRoles = userRoleRepository.findByUserId(userId);

            if (CollectionUtils.isEmpty(userRoles)) {
                return response(user, major, campus, Collections.emptyList());
            }

            List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).toList();

            List<Role> roles = roleRepository.findByIdIn(roleIds);

            return response(user, major, campus, roles);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<UserResponse> save(UserRequest request, Long operatorId) {
        try {
            Long userId = request.getId();

            Long majorId = request.getMajorId();

            Major major = Objects.isNull(majorId) ? null : majorRepository.findById(majorId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy Major với id %s!", majorId)));

            Long campusId = request.getCampusId();

            if (Objects.isNull(campusId)) {
                throw new IllegalArgumentException("Campus Id không được để trống!");
            }

            Campus campus = campusRepository.findById(campusId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy Campus với id %s!", campusId)));

            List<Long> roleIds = request.getRoles();

            if (CollectionUtils.isEmpty(roleIds)) {
                return Objects.isNull(userId)
                        ? create(request, major, campus, Collections.emptyList(), operatorId)
                        : update(request, major, campus, Collections.emptyList(), operatorId);
            }

            List<Role> roles = roleRepository.findByIdIn(roleIds);

            if (roleIds.size() != roles.size()) {
                throw new IllegalArgumentException("Danh sách Role không hợp lệ!");
            }

            return Objects.isNull(userId) ? create(request, major, campus, roles, operatorId) : update(request, major, campus, roles, operatorId);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<UserResponse> create(UserRequest request,
                                             Major major,
                                             Campus campus,
                                             List<Role> roles,
                                             Long operatorId) {
        User user = User.of(request);
        if (StringUtils.hasText(request.getPersonalEmail())) user.setPersonalEmail(request.getPersonalEmail());
        if (StringUtils.hasText(request.getPhoneNumber())) user.setPhoneNumber(request.getPhoneNumber());
        if (StringUtils.hasText(request.getAddress())) user.setAddress(request.getAddress());
        user.setCreatedBy(operatorId);
        user.setCreatedAt(DateUtils.now());

        user = userRepository.save(user);

        Long userId = user.getId();

        if (CollectionUtils.isEmpty(roles)) {
            return response(user, major, campus, Collections.emptyList());
        }

        List<UserRole> userRoles = roles.stream()
                .map(role -> UserRole.of(userId, role.getId(), operatorId))
                .toList();

        userRoleRepository.saveAll(userRoles);

        return response(user, major, campus, roles);
    }

    @Override
    public BaseResponse<UserResponse> update(UserRequest request,
                                             Major major,
                                             Campus campus,
                                             List<Role> roles,
                                             Long operatorId) {
        try {
            Long userId = request.getId();

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy User với id %s!", userId)));

            user.setFullName(request.getFullName());
            user.setRollNumber(request.getRollNumber());
            user.setGender(Gender.of(request.getGender()));
            user.setStatus(StudentStatus.of(request.getStatus()));
            user.setDateOfBirth(request.getDateOfBirth());
            user.setMajorId(request.getMajorId());
            user.setCampusId(request.getCampusId());
            user.setEduEmail(request.getEduEmail());
            if (StringUtils.hasText(request.getPersonalEmail())) user.setPersonalEmail(request.getPersonalEmail());
            if (StringUtils.hasText(request.getPhoneNumber())) user.setPhoneNumber(request.getPhoneNumber());
            if (StringUtils.hasText(request.getAddress())) user.setAddress(request.getAddress());
            user.setOjtStatus(StudentOJTStatus.of(request.getOjtStatus()));
            user.setModifiedBy(operatorId);
            user.setModifiedAt(DateUtils.now());

            user = userRepository.save(user);

            List<UserRole> currentRoles = userRoleRepository.findByUserId(user.getId());

            if (CollectionUtils.isEmpty(currentRoles)) currentRoles = Collections.emptyList();

            List<Long> currentRoleIds = currentRoles.stream().map(UserRole::getRoleId).toList();

            List<Long> requestRoleIds = CollectionUtils.isEmpty(request.getRoles())
                    ? Collections.emptyList()
                    : request.getRoles();

            List<UserRole> removedRoles = currentRoles.stream()
                    .filter(item -> !requestRoleIds.contains(item.getRoleId()))
                    .toList();

            if (!CollectionUtils.isEmpty(removedRoles)) {
                userRoleRepository.deleteAll(removedRoles);
            }

            List<UserRole> newRoles = requestRoleIds.stream()
                    .filter(item -> !currentRoleIds.contains(item))
                    .map(item -> UserRole.of(userId, item, operatorId))
                    .toList();

            if (!CollectionUtils.isEmpty(newRoles)) {
                userRoleRepository.saveAll(newRoles);
            }

            return response(user, major, campus, roles);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<List<UserResponse>> disable(List<Long> userIds, Long operatorId) {
        try {
            List<User> users = userRepository.findByIdIn(userIds);
            if (users.size() != userIds.size()) {
                throw new IllegalArgumentException("Danh sách Id không hợp lệ!");
            }
            users = users.stream()
                    .map(user -> {
                        user.setStatus(StudentStatus.DISABLED);
                        user.setModifiedBy(operatorId);
                        user.setModifiedAt(DateUtils.now());
                        return user;
                    })
                    .toList();
            users = userRepository.saveAll(users);
            List<UserResponse> response = users.stream().map(UserResponse::of).toList();
            return BaseResponse.success(response);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<UserResponse> response(User user, Major major, Campus campus, List<Role> roles) {
        MajorResponse majorResponse = Objects.isNull(major)
                ? null
                : MajorResponse.of(major);

        CampusResponse campusResponse = CampusResponse.of(campus);

        List<RoleResponse> roleResponses = CollectionUtils.isEmpty(roles)
                ? Collections.emptyList()
                : roles.stream().map(RoleResponse::of).toList();

        return BaseResponse.success(UserResponse.of(user, majorResponse, campusResponse, roleResponses));
    }

    @Override
    public BaseResponse<UserResponse> updateProfile(UserRequest request, Long operatorId) {
        try {
            Long userId = request.getId();

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy User với id %s!", userId)));

            user.setFullName(request.getFullName().strip());
            user.setGender(Gender.of(request.getGender()));
            user.setDateOfBirth(request.getDateOfBirth());
            user.setPersonalEmail(request.getPersonalEmail().strip());
            user.setPhoneNumber(request.getPhoneNumber().strip());
            user.setAddress(request.getAddress().strip());
            user.setStatus(StudentStatus.ENABLED);
            user.setModifiedBy(operatorId);
            user.setModifiedAt(DateUtils.now());

            user = userRepository.save(user);

            return BaseResponse.success(UserResponse.of(user));
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<UserResponse> get(String rollNumber) {
        try {
            User user = userRepository.findByRollNumber(rollNumber)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy user với rollNumber %s", rollNumber)));
            Long majorId = user.getMajorId();

            Major major = Objects.isNull(majorId)
                    ? null
                    : majorRepository.findById(majorId).orElse(null);

            Long campusId = user.getCampusId();

            Campus campus = campusRepository.findById(campusId).orElse(null);

            List<UserRole> userRoles = userRoleRepository.findByUserId(user.getId());

            if (CollectionUtils.isEmpty(userRoles)) {
                return response(user, major, campus, Collections.emptyList());
            }

            List<Long> roleIds = userRoles.stream().map(UserRole::getRoleId).toList();

            List<Role> roles = roleRepository.findByIdIn(roleIds);

            return response(user, major, campus, roles);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

}
