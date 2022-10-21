package vn.edu.fpt.capstone.api.responses;

import lombok.*;
import vn.edu.fpt.capstone.api.constants.OJTInfoStatus;
import vn.edu.fpt.capstone.api.entities.RequestAddCompany;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestAddCompanyResponse {
    private Long id;
    private String offerFile;
    private String mouUrl;
    private String taxCode;
    private String name;
    private OJTInfoStatus status;

    public static RequestAddCompanyResponse of(RequestAddCompany requestAddCompany) {
        return RequestAddCompanyResponse.builder()
                .id(requestAddCompany.getId())
                .offerFile(requestAddCompany.getOfferUrl())
                .mouUrl(requestAddCompany.getMouUrl())
                .taxCode(requestAddCompany.getTaxCode())
                .name(requestAddCompany.getName())
                .status(requestAddCompany.getStatus())
                .build();
    }

}
