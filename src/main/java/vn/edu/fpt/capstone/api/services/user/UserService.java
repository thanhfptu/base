package vn.edu.fpt.capstone.api.services.user;

import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import vn.edu.fpt.capstone.api.entities.Major;
import vn.edu.fpt.capstone.api.entities.Role;
import vn.edu.fpt.capstone.api.requests.UserRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.UserResponse;
import vn.edu.fpt.capstone.api.entities.Campus;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.responses.PageResponse;

import java.util.List;

public interface UserService extends UserDetailsService {

    String SERVICE_NAME = "UserService";

    PageResponse<UserResponse> list(Pageable pageable,
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
                                    Integer ojtStatus);

    BaseResponse<UserResponse> get(Long userId);

    BaseResponse<UserResponse> save(UserRequest request,
                                    Long operatorId);

    BaseResponse<UserResponse> create(UserRequest request,
                                      Major major,
                                      Campus campus,
                                      List<Role> roles,
                                      Long operatorId);

    BaseResponse<UserResponse> update(UserRequest request,
                                      Major major,
                                      Campus campus,
                                      List<Role> roles,
                                      Long operatorId);

    BaseResponse<List<UserResponse>> disable(List<Long> userIds,
                                             Long operatorId);

    BaseResponse<UserResponse> response(User user,
                                        Major major,
                                        Campus campus,
                                        List<Role> roles);

    BaseResponse<UserResponse> updateProfile(UserRequest request, Long operatorId);

    BaseResponse<UserResponse> get(String rollNumber);

//    ByteArrayInputStream export() throws IOException;
}
