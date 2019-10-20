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
@RequestMapping("/api/todos")
public class ToDoController {

    @Autowired
    private ToDoRepository toDoRepository;

    @GetMapping
    public ResponseEntity<List<ToDo>> getToDos() {

        if (!toDoRepository.findAll().isEmpty())
            return new ResponseEntity(toDoRepository.findAll(), HttpStatus.ACCEPTED);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToDo> get(@PathVariable Long id) {

        Optional<ToDo> maybeTodo = toDoRepository.findById(id);

        if (maybeTodo.isPresent())
            return new ResponseEntity(maybeTodo.get(), HttpStatus.ACCEPTED);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<List<ToDo>> post(@Valid @RequestBody List<ToDo> toDos) {
        return new ResponseEntity(toDoRepository.saveAll(toDos), HttpStatus.CREATED);
    }

}