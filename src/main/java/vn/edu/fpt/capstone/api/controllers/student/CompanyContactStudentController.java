package vn.edu.fpt.capstone.api.controllers.student;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.controllers.manage.CompanyManageController;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.CompanyContactResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.services.companyContact.CompanyContactService;

@RestController
@RequiredArgsConstructor
@RequestMapping(CompanyContactStudentController.PATH)
public class CompanyContactStudentController {

    public static final String PATH = AppConfig.STUDENT_PATH + AppConfig.V1_PATH + "/company-contacts";

    private final CompanyContactService companyContactService;

    @GetMapping
    @Operation(summary = "Danh sách company contact")
    public ResponseEntity<PageResponse<CompanyContactResponse>> list(@RequestParam Integer currentPage,
                                                                     @RequestParam Integer pageSize,
                                                                     @RequestParam(required = false) String email,
                                                                     @RequestParam(required = false) String phone,
                                                                     @RequestParam(required = false) String name,
                                                                     @RequestParam(required = false) Long companyId,
                                                                     @RequestParam(required = false) Boolean enabled) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);
        PageResponse<CompanyContactResponse> response = companyContactService.list(pageable,
                email,
                phone,
                name,
                companyId,
                enabled);
        return AppResponse.success(response);
    }

}
