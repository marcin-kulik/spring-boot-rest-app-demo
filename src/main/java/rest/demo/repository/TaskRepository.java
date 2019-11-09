package rest.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.demo.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
