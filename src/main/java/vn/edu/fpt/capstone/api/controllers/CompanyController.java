package vn.edu.fpt.capstone.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.CompanyResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.services.company.CompanyService;

@RestController
@RequiredArgsConstructor
@RequestMapping(CompanyController.PATH)
public class CompanyController {

    public static final String PATH = AppConfig.V1_PATH + "/companies";

    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<PageResponse<CompanyResponse>> list(@RequestParam Integer currentPage,
                                                              @RequestParam Integer pageSize,
                                                              @RequestParam(required = false) String name,
                                                              @RequestParam(required = false) String address,
                                                              @RequestParam(required = false) String description,
                                                              @RequestParam(required = false) String website,
                                                              @RequestParam(required = false) String imgUrl,
                                                              @RequestParam(required = false) String taxCode,
                                                              @RequestParam(required = false) Boolean enabled) {
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(currentPage, pageSize, sort);
        PageResponse<CompanyResponse> response = companyService.list(pageable,
                name,
                address,
                description,
                website,
                imgUrl,
                taxCode,
                enabled);
        return AppResponse.success(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<CompanyResponse>> get(@PathVariable("id") Long id) {
        BaseResponse<CompanyResponse> result = companyService.get(id);
        return AppResponse.success(result);
    }

}
