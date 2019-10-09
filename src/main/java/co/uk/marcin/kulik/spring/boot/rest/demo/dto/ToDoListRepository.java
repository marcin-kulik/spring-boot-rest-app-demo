package co.uk.marcin.kulik.spring.boot.rest.demo.dto;

import co.uk.marcin.kulik.spring.boot.rest.demo.model.ToDoList;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Marcin Kulik
 */

public interface ToDoListRepository extends JpaRepository<ToDoList, Long> {

}
