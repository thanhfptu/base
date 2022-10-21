package vn.edu.fpt.capstone.api.services.news;

import org.springframework.data.domain.Pageable;
import vn.edu.fpt.capstone.api.entities.News;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.requests.IdsRequest;
import vn.edu.fpt.capstone.api.requests.NewsRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.NewsResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;

import java.util.Date;
import java.util.List;

public interface NewsService {

    String SERVICE_NAME = "NewsService";

    PageResponse<NewsResponse> list(Pageable pageable,
                                    Long authorId,
                                    String title,
                                    String content,
                                    Boolean isVisible,
                                    Boolean isFeatured,
                                    String thumbnail,
                                    String shortDescription,
                                    Date publishDateFrom,
                                    Date publishDateTo);

    BaseResponse<NewsResponse> get(Long id);

    BaseResponse<NewsResponse> save(NewsRequest request, User operator);

    BaseResponse<NewsResponse> create(NewsRequest request, User operator);

    BaseResponse<NewsResponse> update(NewsRequest request, User operator);

    BaseResponse<NewsResponse> response(News news, User author);

    BaseResponse<List<NewsResponse>> disable(IdsRequest request, Long operatorId);

}
