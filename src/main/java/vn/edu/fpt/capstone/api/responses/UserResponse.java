package vn.edu.fpt.capstone.api.responses;

import lombok.*;
import org.springframework.util.CollectionUtils;
import vn.edu.fpt.capstone.api.constants.Gender;
import vn.edu.fpt.capstone.api.constants.StudentOJTStatus;
import vn.edu.fpt.capstone.api.constants.StudentStatus;
import vn.edu.fpt.capstone.api.entities.User;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String fullName;
    private String rollNumber;
    private Gender gender;
    private StudentStatus status;
    private Date dateOfBirth;
    private MajorResponse major;
    private CampusResponse campus;
    private String eduEmail;
    private String personalEmail;
    private String phoneNumber;
    private String address;
    private StudentOJTStatus ojtStatus;
    private List<RoleResponse> roles;

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .rollNumber(user.getRollNumber())
                .gender(user.getGender())
                .status(user.getStatus())
                .campus(CampusResponse.builder().id(user.getCampusId()).build())
                .major(Objects.isNull(user.getMajorId()) ? null : MajorResponse.builder().id(user.getMajorId()).build())
                .dateOfBirth(user.getDateOfBirth())
                .eduEmail(user.getEduEmail())
                .personalEmail(user.getPersonalEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .ojtStatus(user.getOjtStatus())
                .build();
    }

    public static UserResponse of(User user,
                                  MajorResponse major,
                                  CampusResponse campus,
                                  List<RoleResponse> roles) {
        UserResponse build = UserResponse.of(user);
        build.setMajor(major);
        build.setCampus(campus);
        build.setRoles(CollectionUtils.isEmpty(roles) ? Collections.emptyList() : roles);
        return build;
    }

}
