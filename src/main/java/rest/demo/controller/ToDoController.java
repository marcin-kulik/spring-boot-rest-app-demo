package rest.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.demo.model.ToDo;
import rest.demo.repository.ToDoRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author Marcin Kulik
 */

@RestController
@Slf4j
@RequestMapping("/api")
public class ToDoController {

    @Autowired
    private ToDoRepository toDoRepository;

    @GetMapping("/todos")
    public ResponseEntity<List<ToDo>> getAll() {

        if (!toDoRepository.findAll().isEmpty()) {
            List<ToDo> toDoList = toDoRepository.findAll();
            log.info("Showing all To Do Lists : {}", toDoList);
            return new ResponseEntity(toDoList, HttpStatus.ACCEPTED);
        } else {
            log.info("There are no To Do Lists.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @RequestMapping("/todos/{id}")
    public ResponseEntity<ToDo> get(@PathVariable Long id) {

        Optional<ToDo> maybeTodo = toDoRepository.findById(id);

        if (maybeTodo.isPresent()) {
            log.info("Showing list : {}", maybeTodo.get());
            return new ResponseEntity(maybeTodo.get(), HttpStatus.ACCEPTED);
        } else {
            log.info("This list does not exist.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/todos")
    public ResponseEntity<List<ToDo>> post(@Valid @RequestBody List<ToDo> toDoList) {
        log.info("Creating ToDo List : {}", toDoList);
        return new ResponseEntity(toDoRepository.saveAll(toDoList), HttpStatus.CREATED);
    }
}