package vn.edu.fpt.capstone.api.entities;

import lombok.*;
import vn.edu.fpt.capstone.api.constants.Gender;
import vn.edu.fpt.capstone.api.constants.StudentOJTStatus;
import vn.edu.fpt.capstone.api.constants.StudentStatus;
import vn.edu.fpt.capstone.api.converters.StudentOJTStatusConverter;
import vn.edu.fpt.capstone.api.requests.UserRequest;
import vn.edu.fpt.capstone.api.converters.GenderConverter;
import vn.edu.fpt.capstone.api.converters.StudentStatusConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = User.TABLE_NAME)
public class User extends BaseEntity {

    public static final String TABLE_NAME = "users";

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "roll_number")
    private String rollNumber;

    @Column(name = "gender")
    @Convert(converter = GenderConverter.class)
    private Gender gender;

    @Column(name = "status")
    @Convert(converter = StudentStatusConverter.class)
    private StudentStatus status;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "major_id")
    private Long majorId;

    @Column(name = "campus_id")
    private Long campusId;

    @Column(name = "edu_email")
    private String eduEmail;

    @Column(name = "personal_email")
    private String personalEmail;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "ojt_status")
    @Convert(converter = StudentOJTStatusConverter.class)
    private StudentOJTStatus ojtStatus;

    public static User of(UserRequest user) {
        return User.builder()
                .fullName(user.getFullName())
                .rollNumber(user.getRollNumber())
                .gender(Gender.of(user.getGender()))
                .status(StudentStatus.of(user.getStatus()))
                .dateOfBirth(user.getDateOfBirth())
                .majorId(user.getMajorId())
                .campusId(user.getCampusId())
                .eduEmail(user.getEduEmail())
                .ojtStatus(StudentOJTStatus.of(user.getOjtStatus()))
                .build();
    }

}
