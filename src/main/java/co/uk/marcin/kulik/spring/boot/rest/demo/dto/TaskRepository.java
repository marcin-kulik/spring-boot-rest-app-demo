package co.uk.marcin.kulik.spring.boot.rest.demo.dto;

import co.uk.marcin.kulik.spring.boot.rest.demo.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Marcin Kulik
 */

public interface TaskRepository extends JpaRepository<Task, Long> {
}
