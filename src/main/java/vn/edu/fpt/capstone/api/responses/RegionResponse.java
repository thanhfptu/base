package vn.edu.fpt.capstone.api.responses;

import lombok.*;
import vn.edu.fpt.capstone.api.entities.Region;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegionResponse {

    private Long id;
    private String name;
    private Integer level;
    private RegionResponse parent;

    public static RegionResponse of(Region region) {
        return RegionResponse.builder()
                .id(region.getId())
                .name(region.getName())
                .level(region.getLevel())
                .build();
    }

    public static RegionResponse of(Region region, Region parent) {
        RegionResponse build = RegionResponse.of(region);
        build.setParent(RegionResponse.of(parent));
        return build;
    }
}
