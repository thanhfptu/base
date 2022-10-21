package vn.edu.fpt.capstone.api.responses;

import lombok.*;
import vn.edu.fpt.capstone.api.constants.CampusStatus;
import vn.edu.fpt.capstone.api.entities.Campus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CampusResponse {

    private Long id;
    private String name;
    private String address;
    private RegionResponse region;
    private String phoneNumber;
    private String email;
    private CampusStatus status;

    public static CampusResponse of(Campus campus) {
        return CampusResponse.builder()
                .id(campus.getId())
                .name(campus.getName())
                .address(campus.getAddress())
                .phoneNumber(campus.getPhoneNumber())
                .email(campus.getEmail())
                .status(campus.getStatus())
                .build();
    }

    public static CampusResponse of(Campus campus, RegionResponse region) {
        CampusResponse build = CampusResponse.of(campus);
        build.setRegion(region);
        return build;
    }
}
