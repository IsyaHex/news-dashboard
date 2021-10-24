package uz.task.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import uz.task.domain.News;
import uz.task.domain.User;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long>, PagingAndSortingRepository<News, Long> {

    @EntityGraph(attributePaths = {"user"})
    List<News> findAll();

    Page<News> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    List<News> findAllByUserOrState(User user, Integer state);

}
