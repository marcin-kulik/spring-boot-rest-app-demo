package co.uk.marcin.kulik.spring.boot.rest.demo.controller;

import co.uk.marcin.kulik.spring.boot.rest.demo.model.Task;
import co.uk.marcin.kulik.spring.boot.rest.demo.service.TaskService;
import co.uk.marcin.kulik.spring.boot.rest.demo.service.ToDoService;
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
    private ToDoService toDoService;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks() {

        if (!taskService.findAll().isEmpty()) {
            log.info("Showing all tasks : {}", toDoService.findAllToDos());
            return new ResponseEntity(taskService.findAll(), HttpStatus.ACCEPTED);
        } else {
            log.info("There are no tasks.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long taskId) {

        if (!taskService.findById(taskId).isEmpty()) {
            log.info("Showing task : {}", taskService.findById(taskId));
            return new ResponseEntity(taskService.findAll(), HttpStatus.ACCEPTED);
        } else {
            log.info("Requested task does not exist.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/todolist/{toDoListId}")
    public ResponseEntity<List<Task>> getTasksInAList(@PathVariable("toDoListId") Long toDoListId) {

        if (toDoService.findToDoById(toDoListId).isPresent()) {
            log.info("List found : {}", toDoService.findToDoById(toDoListId));
            return new ResponseEntity(taskService.findAllInList(toDoListId), HttpStatus.ACCEPTED);
        }
        else{
            log.info("List not found.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/todolist/{toDoListId}")
    public ResponseEntity<Task> createTaskInList(@Valid @RequestBody Task task,
                                                 @PathVariable("toDoListId") Long toDoListId) {

        if (toDoService.findToDoById(toDoListId).isPresent()) {
            log.info("List found and task :{} has been added.", task);
            return new ResponseEntity(taskService.createTask(toDoListId, task), HttpStatus.CREATED);
        }
        else {
            log.info("List not found.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }
}
