package uz.task.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import uz.task.constants.NewsStateEnum;
import uz.task.domain.News;
import uz.task.domain.User;
import uz.task.dto.NewsSaveDto;
import uz.task.repository.NewsRepository;
import uz.task.repository.UserRepository;
import uz.task.service.NewsService;
import uz.task.service.UserService;

import java.security.Principal;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
            news.setAcceptDate(dateTimeFormatter(ZonedDateTime.now()));
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

    private void createNews(NewsSaveDto model, News news) {
        User user = userService.findByUsername();
        news.setDescription(model.getDescription());
        news.setUser(user);
        news.setCreateDate(dateTimeFormatter(ZonedDateTime.now()));
        news.setState(NewsStateEnum.UNVERIFIED.getValue());
        if(user.getId() == 1) {
            news.setAcceptDate(dateTimeFormatter(ZonedDateTime.now()));
            news.setState(NewsStateEnum.VERIFIED.getValue());
        }
    }

    @Override
    public List<News> findAll() {
        User user = userService.findByUsername();
        if (user.getId() == 1) {
            return newsRepository.findAll();
        } else {
            return newsRepository.findAllByUserOrState(user, NewsStateEnum.VERIFIED.getValue());
        }
    }

    @Override
    public Page<News> findAllByPages() {
        return newsRepository.findAll(PageRequest.of(0, 10));
    }

    // Change the displayed date-time format
    private String dateTimeFormatter(ZonedDateTime time) {
        time = ZonedDateTime.now();
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
