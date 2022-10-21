package vn.edu.fpt.capstone.api.responses;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OTPAuthenticationResponse {

    private Long requestId;
    private String email;
    private String accessToken;

    public static OTPAuthenticationResponse of(Long requestId, String email, String accessToken) {
        return OTPAuthenticationResponse.builder()
                .requestId(requestId)
                .email(email)
                .accessToken(accessToken)
                .build();
    }

}
