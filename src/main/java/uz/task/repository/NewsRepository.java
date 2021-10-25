package uz.task.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.task.domain.News;
import uz.task.domain.User;

/**
 * Repository to retrieve
 */
@Repository
public interface NewsRepository extends JpaRepository<News, Long>{


    @EntityGraph(attributePaths = {"user"})
    Page<News> findAll(Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    Page<News> findAllByUserOrState(User user, Integer state, Pageable pageable);

}
