package vn.edu.fpt.capstone.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.CampusResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.services.campus.CampusService;

@RestController
@RequiredArgsConstructor
@RequestMapping(CampusController.PATH)
public class CampusController {

    public static final String PATH = AppConfig.V1_PATH + "/campuses";

    private final CampusService campusService;

    @GetMapping
    @Operation(summary = "Danh sách Campus")
    public ResponseEntity<PageResponse<CampusResponse>> list(@RequestParam Integer currentPage,
                                                             @RequestParam Integer pageSize,
                                                             @RequestParam(required = false) String name,
                                                             @RequestParam(required = false) String address,
                                                             @RequestParam(required = false) Long regionId,
                                                             @RequestParam(required = false) String phoneNumber,
                                                             @RequestParam(required = false) String email,
                                                             @RequestParam(required = false) Integer status) {
        Pageable pageable = PageRequest.of(currentPage, pageSize, Sort.by("id").descending());
        return AppResponse.success(campusService.list(pageable, name, address, regionId, phoneNumber, email, status));
    }

}
