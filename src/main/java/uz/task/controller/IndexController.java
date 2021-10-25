package uz.task.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.task.domain.News;
import uz.task.domain.User;
import uz.task.dto.NewsSaveDto;
import uz.task.service.NewsService;
import uz.task.service.UserService;

import java.util.List;

@Controller
@RequestMapping({"/", "/index"})
public class IndexController {

    private final NewsService newsService;
    private final UserService userService;

    public IndexController(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public String main(Model model) {
        return findByPage(1, model);
    }

    @GetMapping("/{pageNumber}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public String findByPage(@PathVariable(value = "pageNumber") Integer pageNumber, Model model) {
        Integer pageSize = 15;
        Page<News> page = newsService.findAllByPages(pageNumber, pageSize);
        List<News> list = page.getContent();
        User user = userService.findByUsername();
        model.addAttribute("checkUser", user);
        model.addAttribute("news", new NewsSaveDto());
        model.addAttribute("article", new News());
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("articles", list);
        return "index";
    }
}
