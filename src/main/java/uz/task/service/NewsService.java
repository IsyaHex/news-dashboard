package uz.task.service;

import org.springframework.data.domain.Page;
import uz.task.domain.News;
import uz.task.dto.NewsSaveDto;

import java.util.List;

public interface NewsService {
    News save(NewsSaveDto model);
    News update(NewsSaveDto model);
    List<News> findAll();
    News getNewsById(Long id);
    News approveState(Long id);
    News rejectState(Long id);
    Page<News> findAllByPages();
}
