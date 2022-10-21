package vn.edu.fpt.capstone.api.responses;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public final class PageResponse<T> extends BaseResponse<List<T>> {

    private Pagination pagination;

    public static <T> PageResponse<T> success(List<T> data, Integer currentPage, Integer pageSize, Long totalEntries, Long totalPages) {
        PageResponse<T> response = new PageResponse<>();
        response.setSuccess(true);
        response.setMessage("Thành công");
        response.setPagination(Pagination.of(currentPage, pageSize, totalEntries, totalPages));
        response.setData(data);
        return response;
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Pagination {

        private Integer currentPage;
        private Integer pageSize;
        private Long totalEntries;
        private Long totalPages;

        public static Pagination of(Integer currentPage, Integer pageSize, Long totalEntries, Long totalPages) {
            return Pagination.builder()
                    .currentPage(currentPage)
                    .pageSize(pageSize)
                    .totalEntries(totalEntries)
                    .totalPages(totalPages)
                    .build();
        }

    }

}
