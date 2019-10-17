package rest.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rest.demo.model.Task;
import rest.demo.repository.TaskRepository;
import rest.demo.repository.ToDoRepository;
import rest.demo.service.TaskService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Marcin Kulik
 */

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAll() {

        if (!taskRepository.findAll().isEmpty())
            return new ResponseEntity(taskRepository.findAll(), HttpStatus.ACCEPTED);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getById(@PathVariable Long taskId) {

        if (!taskRepository.findById(taskId).isEmpty())
            return new ResponseEntity(taskRepository.findAll(), HttpStatus.ACCEPTED);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/todolist/{toDoListId}")
    public ResponseEntity<List<Task>> getAllInToDo(@PathVariable("toDoListId") Long toDoListId) {

        if (toDoRepository.findById(toDoListId).isPresent())
            return new ResponseEntity(taskService.findAllInList(toDoListId), HttpStatus.ACCEPTED);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/todolist/{toDoListId}")
    public ResponseEntity<Task> postInToDo(@Valid @RequestBody Task task,
                                                 @PathVariable("toDoListId") Long toDoListId) {

        if (toDoRepository.findById(toDoListId).isPresent())
            return new ResponseEntity(taskService.createTask(toDoListId, task), HttpStatus.CREATED);
        else
            return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
