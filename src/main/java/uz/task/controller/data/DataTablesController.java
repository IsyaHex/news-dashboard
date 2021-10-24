package uz.task.controller.data;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.task.constants.NewsStateEnum;
import uz.task.domain.News;
import uz.task.domain.User;
import uz.task.service.NewsService;

import java.util.List;

@RestController
public class DataTablesController {
    private final NewsService newsService;


    public DataTablesController(NewsService newsService) {
        this.newsService = newsService;
    }

    @PostMapping("ajax/news")
    public List<News> listOfNews() {
        return newsService.findAll();
    }
}
