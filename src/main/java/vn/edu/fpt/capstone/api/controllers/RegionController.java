package vn.edu.fpt.capstone.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.RegionResponse;
import vn.edu.fpt.capstone.api.services.region.RegionService;

@RestController
@RequiredArgsConstructor
@RequestMapping(RegionController.PATH)
@Tag(name = "Region", description = "Hiển thị Region")
public class RegionController {

    public static final String PATH = AppConfig.V1_PATH + "/regions";

    private final RegionService regionService;

    @GetMapping
    @Operation(summary = "Danh sách Region")
    public ResponseEntity<PageResponse<RegionResponse>> list(@RequestParam Integer currentPage,
                                                             @RequestParam Integer pageSize,
                                                             @RequestParam(required = false) String name,
                                                             @RequestParam(required = false) Integer level,
                                                             @RequestParam(required = false) Long parentId) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        return AppResponse.success(regionService.list(pageable, level, name, parentId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin Region từ id")
    public ResponseEntity<BaseResponse<RegionResponse>> get(@PathVariable("id") Long id) {
        return AppResponse.success(regionService.get(id));
    }
}
