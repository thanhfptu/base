package vn.edu.fpt.capstone.api.controllers.manage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.models.AppUserDetails;
import vn.edu.fpt.capstone.api.requests.IdsRequest;
import vn.edu.fpt.capstone.api.requests.NewsRequest;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.NewsResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.services.news.NewsService;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(NewsManageController.PATH)
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "News", description = "Quản lý News")
public class NewsManageController {
    public static final String PATH = AppConfig.MANAGE_PATH + AppConfig.V1_PATH + "/news";

    private final NewsService newsService;

    @GetMapping
    @Operation(summary = "Danh sách news")
    public ResponseEntity<PageResponse<NewsResponse>> list(@RequestParam Integer currentPage,
                                                           @RequestParam Integer pageSize,
                                                           @RequestParam(required = false) Long authorId,
                                                           @RequestParam(required = false) String title,
                                                           @RequestParam(required = false) String content,
                                                           @RequestParam(required = false) Boolean isVisible,
                                                           @RequestParam(required = false) Boolean isFeatured,
                                                           @RequestParam(required = false) String thumbnail,
                                                           @RequestParam(required = false) String shortDescription,
                                                           @RequestParam(required = false) @DateTimeFormat(pattern = DateUtils.DATE_FORMAT) Date publishDateFrom,
                                                           @RequestParam(required = false) @DateTimeFormat(pattern = DateUtils.DATE_FORMAT) Date publishDateTo,
                                                           @RequestParam(required = false) String sortBy) {

        Sort sort;
        if (StringUtils.hasText(sortBy)) {
            if (sortBy.charAt(0) == '-') {
                sort = Sort.by(sortBy.substring(1)).descending();
            } else {
                sort = Sort.by(sortBy.substring(1)).ascending();
            }
        } else {
            sort = Sort.by("id").descending();
        }

        Pageable pageable = PageRequest.of(currentPage, pageSize, sort);

        PageResponse<NewsResponse> response = newsService.list(
                pageable,
                authorId,
                title,
                content,
                isVisible,
                isFeatured,
                thumbnail,
                shortDescription,
                publishDateFrom,
                publishDateTo
        );
        return AppResponse.success(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin news từ id")
    public ResponseEntity<BaseResponse<NewsResponse>> get(@PathVariable("id") Long id) {
        BaseResponse<NewsResponse> result = newsService.get(id);
        return AppResponse.success(result);
    }

    @PostMapping
    @Operation(summary = "Tạo news")
    public ResponseEntity<BaseResponse<NewsResponse>> create(Authentication authentication,
                                                             @Valid @RequestBody NewsRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        return AppResponse.success(newsService.save(request, operator));
    }

    @PutMapping
    @Operation(summary = "Cập nhật news")
    public ResponseEntity<BaseResponse<NewsResponse>> update(Authentication authentication,
                                                             @Valid @RequestBody NewsRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        return AppResponse.success(newsService.save(request, operator));
    }

    @PostMapping("/disable")
    @Operation(summary = "Ẩn News")
    public ResponseEntity<BaseResponse<List<NewsResponse>>> disable(Authentication authentication,
                                                                    @Valid @RequestBody IdsRequest request) {
        AppUserDetails userDetails = (AppUserDetails) authentication.getPrincipal();
        User operator = userDetails.user();
        Long operatorId = operator.getId();
        return AppResponse.success(newsService.disable(request, operatorId));
    }

}
