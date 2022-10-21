package vn.edu.fpt.capstone.api.responses;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OTPResponse {

    private Long requestId;

    private String email;

    public static OTPResponse of(Long requestId, String email) {
        return OTPResponse.builder()
                .requestId(requestId)
                .email(email)
                .build();
    }

}
