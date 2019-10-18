package rest.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.demo.model.Task;
import rest.demo.model.ToDo;
import rest.demo.repository.ToDoRepository;
import rest.demo.service.TaskService;

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

    @Autowired
    private TaskService taskService;

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

    @GetMapping("/{id}/tasks")
    public ResponseEntity<List<Task>> getToDos(@PathVariable("id") Long id) {

        if (toDoRepository.findById(id).isPresent())
            return new ResponseEntity(taskService.findTasks(id), HttpStatus.ACCEPTED);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}/tasks")
    public ResponseEntity<Task> postTask(@Valid @RequestBody Task task,
                                           @PathVariable("id") Long id) {

        if (toDoRepository.findById(id).isPresent())
            return new ResponseEntity(taskService.create(id, task), HttpStatus.CREATED);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }


}