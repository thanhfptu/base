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
import vn.edu.fpt.capstone.api.responses.MajorResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.services.major.MajorService;


@RestController
@RequiredArgsConstructor
@RequestMapping(MajorController.PATH)
@Tag(name = "Major", description = "Hiển thị Major")
public class MajorController {

    public static final String PATH = AppConfig.V1_PATH + "/majors";

    private final MajorService majorService;

    @GetMapping
    @Operation(summary = "Danh sách Major")
    public ResponseEntity<PageResponse<MajorResponse>> list(@RequestParam Integer currentPage,
                                                            @RequestParam Integer pageSize,
                                                            @RequestParam(required = false) String code,
                                                            @RequestParam(required = false) String name,
                                                            @RequestParam(required = false) Integer level,
                                                            @RequestParam(required = false) Long parentId) {
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        return AppResponse.success(majorService.list(pageable, code, name, level, parentId, true));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin Major từ id")
    public ResponseEntity<BaseResponse<MajorResponse>> get(@PathVariable("id") Long id) {
        return AppResponse.success(majorService.get(id));
    }
}
