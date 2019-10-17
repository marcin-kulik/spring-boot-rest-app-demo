package rest.demo.controller;

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
@RequestMapping("/api")
public class ToDoController {

    @Autowired
    private ToDoRepository toDoRepository;

    @GetMapping("/todos")
    public ResponseEntity<List<ToDo>> getAll() {

        if (!toDoRepository.findAll().isEmpty())
            return new ResponseEntity(toDoRepository.findAll(), HttpStatus.ACCEPTED);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<ToDo> get(@PathVariable Long id) {

        Optional<ToDo> maybeTodo = toDoRepository.findById(id);

        if (maybeTodo.isPresent())
            return new ResponseEntity(maybeTodo.get(), HttpStatus.ACCEPTED);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/todos")
    public ResponseEntity<List<ToDo>> post(@Valid @RequestBody List<ToDo> toDoList) {

        return new ResponseEntity(toDoRepository.saveAll(toDoList), HttpStatus.CREATED);
    }
}