package co.uk.marcin.kulik.spring.boot.rest.demo.repository;

import co.uk.marcin.kulik.spring.boot.rest.demo.model.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Marcin Kulik
 */

public interface ToDoRepository extends JpaRepository<ToDo, Long> {

}
