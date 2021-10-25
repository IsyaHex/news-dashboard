package uz.task.controller.assets;

import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.task.domain.News;
import uz.task.domain.User;
import uz.task.dto.NewsSaveDto;
import uz.task.service.NewsService;
import uz.task.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/news")
public class NewsController {
    private final NewsService newsService;
    private final UserService userService;
    private static final String url = "http://localhost:5665/news";

    public NewsController(NewsService newsService, UserService userService) {
        this.newsService = newsService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public String main(Model model) {
        return findByPage(1, model);
    }

    @GetMapping("/{pageNumber}")
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
        return "_assets/news";
    }

    @RequestMapping(value = "/saveNews", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public String saveNews(@ModelAttribute(name = "news") NewsSaveDto news, Model model) {
        newsService.save(news);
        model.addAttribute("news", news);
        return "redirect:" + url;
    }

    @RequestMapping(value = "/updateNews", method = RequestMethod.POST)
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public String updateNews(@ModelAttribute(name = "news") NewsSaveDto news, Model model) {
        newsService.update(news);
        model.addAttribute("news", news);
        return "redirect:" + url;
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public String showFormForEdit(@PathVariable("id") Long id, Model model) {
        User user = userService.findByUsername();
        News news = newsService.getNewsById(id);
        if (user.getId().equals(news.getUser().getId()) || user.getId() == 1) {
            model.addAttribute("news", news);
            return "_assets/edit";
        } else {
            return "redirect:" + url;
        }
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    public String showFormDetails(@PathVariable("id") Long id, Model model) {
        News news = newsService.getNewsById(id);
        model.addAttribute("news", news);
        return "_assets/details";
    }

    @GetMapping("/approve/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String approveArticle(@PathVariable("id") Long id) {
        newsService.approveState(id);
        return "redirect:" + url + "/1";
    }

    @GetMapping("/reject/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String rejectArticle(@PathVariable("id") Long id) {
        newsService.rejectState(id);
        return "redirect:" + url + "/1";
    }

}
