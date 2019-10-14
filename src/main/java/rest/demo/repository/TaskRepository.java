package rest.demo.repository;

import rest.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Marcin Kulik
 */

public interface TaskRepository extends JpaRepository<Task, Long> {
}
