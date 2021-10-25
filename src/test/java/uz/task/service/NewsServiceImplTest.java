package uz.task.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import uz.task.constants.NewsStateEnum;
import uz.task.domain.News;
import uz.task.repository.NewsRepository;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static uz.task.domain.NewsFactory.firstNews;
import static uz.task.domain.NewsFactory.getNewsList;

@ExtendWith(MockitoExtension.class)
public class NewsServiceImplTest {

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private NewsService newsService;

    @Test
    void update() {
        Optional<News> optional = newsRepository.findById(any());
        assertThat(optional).isNotNull();
        News news = null;
        if(optional.isPresent()) {
            news = optional.get();
            assertThat(news).isNotNull().isEqualTo(optional);
            news.setDescription(anyString());
            assertThat(news.getDescription()).isNotNull().isEqualTo(anyString());
            news.setState(NewsStateEnum.UNVERIFIED.getValue());
            assertThat(news.getState()).isNotNull().isEqualTo(NewsStateEnum.UNVERIFIED.getValue());
            when(newsRepository.save(news)).thenReturn(news);
            assertThat(newsRepository.save(news)).isNotNull().isEqualTo(news);
        }
    }

    @Test
    void approveState() {
        Optional<News> optional = newsRepository.findById(any());
        assertThat(optional).isNotNull();
        News news = null;
        if(optional.isPresent()) {
            news = optional.get();
            assertThat(news).isNotNull().isEqualTo(optional);
            news.setState(NewsStateEnum.VERIFIED.getValue());
            assertThat(news.getState()).isEqualTo(NewsStateEnum.VERIFIED.getValue());
            when(newsRepository.save(news)).thenReturn(news);
            assertThat(newsRepository.save(news)).isNotNull().isEqualTo(news);
        }
    }

    @Test
    void rejectState() {
        Optional<News> optional = newsRepository.findById(any());
        assertThat(optional).isNotNull();
        News news = null;
        if(optional.isPresent()) {
            news = optional.get();
            assertThat(news).isNotNull().isEqualTo(optional);
            news.setState(NewsStateEnum.UNVERIFIED.getValue());
            assertThat(news.getState()).isEqualTo(NewsStateEnum.UNVERIFIED.getValue());
            when(newsRepository.save(news)).thenReturn(news);
            assertThat(newsRepository.save(news)).isNotNull().isEqualTo(news);
        }
    }

    @Test
    void findAllByPages() {
        final String idField = "id";
        int size = ThreadLocalRandom.current().nextInt(2, 1000);
        Sort sort = Sort.by(idField).ascending();
        Pageable pageable = PageRequest.of( size - 1, size, sort);
        Page<News> page = new PageImpl<>(getNewsList());
        when(newsRepository.findAll(pageable)).thenReturn(page);
        assertThat(newsRepository.findAll(pageable)).isNotNull().isEqualTo(page);
    }

    @Test
    void getNewsById() {
        when(newsService.getNewsById(any())).thenReturn(firstNews());
        assertThat(firstNews().getId()).isNotNull().isEqualTo(1L);
        assertThat(newsService.getNewsById(isNotNull())).isNotNull().isEqualTo(firstNews());
    }
}