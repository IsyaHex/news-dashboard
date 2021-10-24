package uz.task.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.task.domain.News;
import uz.task.dto.NewsSaveDto;
import uz.task.service.NewsService;

@Controller
@RequestMapping({"/", "/index"})
public class IndexController {

    private final NewsService newsService;

    public IndexController(NewsService newsService) {
        this.newsService = newsService;
    }

    @GetMapping
    public String main(Model model) {
        model.addAttribute("news", new NewsSaveDto());
        model.addAttribute("articles", newsService.findAll());
        model.addAttribute("article", new News());
        return "/index";
    }
}
