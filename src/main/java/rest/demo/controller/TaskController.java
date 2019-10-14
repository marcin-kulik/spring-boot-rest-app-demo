package rest.demo.controller;

import rest.demo.model.Task;
import rest.demo.repository.TaskRepository;
import rest.demo.repository.ToDoRepository;
import rest.demo.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Marcin Kulik
 */

@RestController
@Slf4j
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

        if (!taskRepository.findAll().isEmpty()) {
            log.info("Showing all tasks : {}", toDoRepository.findAll());
            return new ResponseEntity(taskRepository.findAll(), HttpStatus.ACCEPTED);
        } else {
            log.info("There are no tasks.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getById(@PathVariable Long taskId) {

        if (!taskRepository.findById(taskId).isEmpty()) {
            log.info("Showing task : {}", taskRepository.findById(taskId));
            return new ResponseEntity(taskRepository.findAll(), HttpStatus.ACCEPTED);
        } else {
            log.info("Requested task does not exist.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/todolist/{toDoListId}")
    public ResponseEntity<List<Task>> getAllInToDo(@PathVariable("toDoListId") Long toDoListId) {

        if (toDoRepository.findById(toDoListId).isPresent()) {
            log.info("List found : {}", toDoRepository.findById(toDoListId));
            return new ResponseEntity(taskService.findAllInList(toDoListId), HttpStatus.ACCEPTED);
        }
        else{
            log.info("List not found.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/todolist/{toDoListId}")
    public ResponseEntity<Task> postInToDo(@Valid @RequestBody Task task,
                                                 @PathVariable("toDoListId") Long toDoListId) {

        if (toDoRepository.findById(toDoListId).isPresent()) {
            log.info("List found and task :{} has been added.", task);
            return new ResponseEntity(taskService.createTask(toDoListId, task), HttpStatus.CREATED);
        }
        else {
            log.info("List not found.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
