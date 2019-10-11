package co.uk.marcin.kulik.spring.boot.rest.demo.controller;

import co.uk.marcin.kulik.spring.boot.rest.demo.model.ToDo;
import co.uk.marcin.kulik.spring.boot.rest.demo.service.ToDoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author Marcin Kulik
 */

@RestController
@Slf4j
@RequestMapping("/api")
public class ToDoListController {

    @Autowired
    private ToDoService toDoService;

    @GetMapping("/todos")
    public ResponseEntity<List<ToDo>> getAll() {

        if (!toDoService.findAllToDos().isEmpty()) {
            List<ToDo> todoLIst = toDoService.findAllToDos();
            log.info("Showing all To Do Lists : {}", todoLIst);
            return new ResponseEntity(todoLIst, HttpStatus.ACCEPTED);
        } else {
            log.info("There are no To Do Lists.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @RequestMapping("/todos/{id}")
    public ResponseEntity<ToDo> get(@PathVariable Long id) {

        Optional<ToDo> maybeTodo = toDoService.findToDoById(id);
        ToDo todo = maybeTodo.get();

        if (maybeTodo.isPresent()) {
            log.info("Showing list : {}", maybeTodo.get());
            return new ResponseEntity(maybeTodo.get(), HttpStatus.ACCEPTED);
        } else {
            log.info("This list does not exist.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/todos")
    public ResponseEntity<ToDo> post(@Valid @RequestBody List<ToDo> toDoList) {
        log.info("Creating ToDo List : {}", toDoList);
        return new ResponseEntity(toDoService.saveToDos(toDoList), HttpStatus.CREATED);
    }
}