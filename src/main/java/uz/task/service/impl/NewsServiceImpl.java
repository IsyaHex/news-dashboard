package uz.task.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import uz.task.constants.NewsStateEnum;
import uz.task.domain.News;
import uz.task.domain.User;
import uz.task.dto.NewsSaveDto;
import uz.task.repository.NewsRepository;
import uz.task.service.NewsService;
import uz.task.service.UserService;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class NewsServiceImpl implements NewsService {

    private final NewsRepository newsRepository;
    private final UserService userService;

    public NewsServiceImpl(NewsRepository newsRepository, UserService userService) {
        this.newsRepository = newsRepository;
        this.userService = userService;
    }

    @Override
    public News save(NewsSaveDto model) {
        News news = new News();
        createNews(model, news);
        return newsRepository.save(news);
    }

    @Override
    public News update(NewsSaveDto model) {
        Optional<News> optional = newsRepository.findById(model.getId());
        News news = null;
        if(optional.isPresent()) {
            news = optional.get();
            news.setDescription(model.getDescription());
            news.setState(NewsStateEnum.UNVERIFIED.getValue());
            return newsRepository.save(news);
        } else {
            throw new RuntimeException("News not found for id: " + model.getId());
        }
    }

    @Override
    public News approveState(Long id) {
        Optional<News> optional = newsRepository.findById(id);
        News news = null;
        if(optional.isPresent()) {
            news = optional.get();
            news.setState(NewsStateEnum.VERIFIED.getValue());
            news.setAcceptDate(dateTimeFormatter());
            return newsRepository.save(news);
        } else {
            throw new RuntimeException("News not found for id: " + id);
        }
    }

    @Override
    public News rejectState(Long id) {
        Optional<News> optional = newsRepository.findById(id);
        News news = null;
        if(optional.isPresent()) {
            news = optional.get();
            news.setState(NewsStateEnum.UNVERIFIED.getValue());
            news.setAcceptDate("Rejected!");
            return newsRepository.save(news);
        } else {
            throw new RuntimeException("News not found for id: " + id);
        }
    }

    @Override
    public Page<News> findAllByPages(Integer pageNumber, Integer size) {
        User user = userService.findByUsername();
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, size, sort);
        if (user.getId() == 1) {
            return newsRepository.findAll(pageable);
        } else {
            return newsRepository.findAllByUserOrState(user, NewsStateEnum.VERIFIED.getValue(), pageable);
        }
    }

    private void createNews(NewsSaveDto model, News news) {
        User user = userService.findByUsername();
        news.setDescription(model.getDescription());
        news.setUser(user);
        news.setCreateDate(dateTimeFormatter());
        news.setState(NewsStateEnum.UNVERIFIED.getValue());
        if(user.getId() == 1) {
            news.setAcceptDate(dateTimeFormatter());
            news.setState(NewsStateEnum.VERIFIED.getValue());
        }
    }

    // Change the displayed date-time format
    private String dateTimeFormatter() {
        ZonedDateTime time = ZonedDateTime.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return time.format(format);
    }

    @Override
    public News getNewsById(Long id) {
        Optional<News> optional = newsRepository.findById(id);
        News news = null;
        if(optional.isPresent()) {
            news = optional.get();
        } else {
            throw new RuntimeException("Employee not found for id: " + id);
        }
        return news;
    }
}
