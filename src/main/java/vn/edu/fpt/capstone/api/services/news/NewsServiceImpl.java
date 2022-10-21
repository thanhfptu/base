package vn.edu.fpt.capstone.api.services.news;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import vn.edu.fpt.capstone.api.constants.SearchOperation;
import vn.edu.fpt.capstone.api.entities.News;
import vn.edu.fpt.capstone.api.entities.User;
import vn.edu.fpt.capstone.api.models.specification.BaseSpecification;
import vn.edu.fpt.capstone.api.models.specification.SearchCriteria;
import vn.edu.fpt.capstone.api.repositories.NewsRepository;
import vn.edu.fpt.capstone.api.repositories.UserRepository;
import vn.edu.fpt.capstone.api.requests.IdsRequest;
import vn.edu.fpt.capstone.api.requests.NewsRequest;
import vn.edu.fpt.capstone.api.responses.BaseResponse;
import vn.edu.fpt.capstone.api.responses.NewsResponse;
import vn.edu.fpt.capstone.api.responses.PageResponse;
import vn.edu.fpt.capstone.api.responses.UserResponse;
import vn.edu.fpt.capstone.api.utils.DateUtils;

import java.time.LocalTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Service(NewsService.SERVICE_NAME)
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;

    private final UserRepository userRepository;

    @Override
    public PageResponse<NewsResponse> list(Pageable pageable,
                                           Long authorId,
                                           String title,
                                           String content,
                                           Boolean isVisible,
                                           Boolean isFeatured,
                                           String thumbnail,
                                           String shortDescription,
                                           Date publishDateFrom,
                                           Date publishDateTo) {
        BaseSpecification<News> specification = new BaseSpecification<>();

        if (StringUtils.hasText(title)) {
            specification.add(new SearchCriteria("title", title, SearchOperation.MATCH));
        }
        if (StringUtils.hasText(content)) {
            specification.add(new SearchCriteria("content", content, SearchOperation.MATCH));
        }
        if (StringUtils.hasText(shortDescription)) {
            specification.add(new SearchCriteria("shortDescription", shortDescription, SearchOperation.MATCH));
        }
        if (Objects.nonNull(authorId)) {
            specification.add(new SearchCriteria("authorId", authorId, SearchOperation.EQUAL));
        }
        if (Objects.nonNull(isVisible)) {
            specification.add(new SearchCriteria("isVisible", isVisible, SearchOperation.EQUAL));
        }
        if (Objects.nonNull(isFeatured)) {
            specification.add(new SearchCriteria("isFeatured", isFeatured, SearchOperation.EQUAL));
        }
        if (Objects.nonNull(publishDateFrom)) {
            specification.add(new SearchCriteria("publishDate", DateUtils.at(DateUtils.toLocalDate(publishDateFrom), LocalTime.MIN), SearchOperation.GREATER_THAN_EQUAL));
        }
        if (Objects.nonNull(publishDateTo)) {
            specification.add(new SearchCriteria("publishDate", DateUtils.at(DateUtils.toLocalDate(publishDateTo), LocalTime.MAX), SearchOperation.LESS_THAN_EQUAL));
        }

        Page<News> page = newsRepository.findAll(specification, pageable);

        List<News> contentList = CollectionUtils.isEmpty(page.getContent()) ? Collections.emptyList() : page.getContent();

        List<NewsResponse> data = contentList.stream()
                .map(NewsResponse::of)
                .toList();

        return PageResponse.success(data, page.getNumber(), page.getSize(), page.getTotalElements(), (long) page.getTotalPages());
    }

    @Override
    public BaseResponse<NewsResponse> get(Long id) {
        try {
            News news = newsRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy news với id %s", id)));

            User author = userRepository.findById(news.getAuthorId()).orElse(null);

            return response(news, author);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<NewsResponse> save(NewsRequest request, User operator) {
        try {
            return Objects.isNull(request.getId()) ? create(request, operator) : update(request, operator);
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<NewsResponse> create(NewsRequest request, User operator) {

        News news = News.of(request, operator.getId());

        news.setAuthorId(operator.getId());

        news = newsRepository.save(news);

        return response(news, operator);
    }

    @Override
    public BaseResponse<NewsResponse> update(NewsRequest request, User operator) {
        try {
            Date publishDate = DateUtils.at(DateUtils.toLocalDate(request.getPublishDate()), LocalTime.MIN);

            News news = newsRepository.findById(request.getId())
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy news với id %s", request.getId())));

            news.setTitle(request.getTitle().strip());
            news.setContent(request.getContent().strip());
            news.setIsVisible(request.getIsVisible());
            news.setIsFeatured(request.getIsFeatured());
            news.setThumbnail(request.getThumbnail().strip());
            news.setShortDescription(request.getShortDescription().strip());
            news.setPublishDate(publishDate);
            news.setIsVisible(publishDate.compareTo(DateUtils.now()) <= 0);
            news.setModifiedAt(DateUtils.now());
            news.setModifiedBy(operator.getId());

            news = newsRepository.save(news);

            Long authorId = news.getAuthorId();

            User author = userRepository.findById(authorId)
                    .orElseThrow(() -> new IllegalArgumentException(String.format("Không tìm thấy author với id %s", authorId)));

            return response(news, author);

        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

    @Override
    public BaseResponse<NewsResponse> response(News news, User author) {
        UserResponse userResponse = Objects.isNull(author) ? null : UserResponse.of(author);
        return BaseResponse.success(NewsResponse.of(news, userResponse));
    }

    @Override
    public BaseResponse<List<NewsResponse>> disable(IdsRequest request, Long operatorId) {
        try {
            List<Long> ids = request.getIds();
            List<News> newsList = newsRepository.findByIdIn(ids);
            if (newsList.size() != ids.size()) {
                throw new IllegalArgumentException("Danh sách Id không hợp lệ!");
            }
            newsList = newsRepository.saveAll(newsList.stream()
                    .map((news) -> {
                        news.setIsVisible(false);
                        news.setModifiedBy(operatorId);
                        news.setModifiedAt(DateUtils.now());
                        return news;
                    })
                    .toList());
            return BaseResponse.success(newsList.stream().map(NewsResponse::of).toList());
        } catch (IllegalArgumentException e) {
            return BaseResponse.error(e.getMessage());
        }
    }

}
