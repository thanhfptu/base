package vn.edu.fpt.capstone.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.capstone.api.configs.AppConfig;
import vn.edu.fpt.capstone.api.responses.AppResponse;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.NewsResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.services.news.NewsService;

@RestController
@RequiredArgsConstructor
@RequestMapping(NewsController.PATH)
@Tag(name = "News", description = "Hiển thị News")
public class NewsController {
    public static final String PATH = AppConfig.V1_PATH + "/news";

    private final NewsService newsService;

    @GetMapping
    @Operation(summary = "Danh sách news")
    public ResponseEntity<PageResponse<NewsResponse>> list(@RequestParam Integer currentPage,
                                                           @RequestParam Integer pageSize,
                                                           @RequestParam(required = false) Boolean isFeatured) {
        Sort sort = Sort.by("id").descending();
        Pageable pageable = PageRequest.of(currentPage, pageSize, sort);

        PageResponse<NewsResponse> response = newsService.list(
                pageable,
                null,
                null,
                null,
                true,
                isFeatured,
                null,
                null,
                null,
                null
        );
        return AppResponse.success(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Lấy thông tin news từ id")
    public ResponseEntity<BaseResponse<NewsResponse>> get(@PathVariable("id") Long id) {
        BaseResponse<NewsResponse> result = newsService.get(id);
        return AppResponse.success(result);
    }
}
