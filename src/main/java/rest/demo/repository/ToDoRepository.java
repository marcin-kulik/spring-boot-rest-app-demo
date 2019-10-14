package rest.demo.repository;

import rest.demo.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Marcin Kulik
 */

public interface ToDoRepository extends JpaRepository<ToDo, Long> {

}
