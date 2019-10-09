package co.uk.marcin.kulik.spring.boot.rest.demo.controller;

import co.uk.marcin.kulik.spring.boot.rest.demo.model.ToDoList;
import co.uk.marcin.kulik.spring.boot.rest.demo.service.TaskService;
import co.uk.marcin.kulik.spring.boot.rest.demo.service.ToDoListService;
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
@RequestMapping("/api/todolist")
public class ToDoListController {

    @Autowired
    private ToDoListService toDoListService;

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<ToDoList>> getToDoLists() {

        if (!toDoListService.findAll().isEmpty()) {
            log.info("Showing all To Do Lists : {}", toDoListService.findAll());
            return new ResponseEntity(toDoListService.findAll(), HttpStatus.ACCEPTED);
        } else {
            log.info("There are no To Do Lists.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @RequestMapping("/{toDoListId")
    public ResponseEntity<ToDoList> getToDoListsById(@PathVariable Long toDoListId) {

        if (!toDoListService.findAll().isEmpty()) {
            log.info("Showing list : {}", toDoListService.findById(toDoListId));
            return new ResponseEntity(toDoListService.findById(toDoListId), HttpStatus.ACCEPTED);
        } else {
            log.info("This list does not exist.");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ToDoList> createToDoList(@Valid @RequestBody ToDoList toDoList) {
        log.info("Creating ToDo List : {}", toDoList);
        return new ResponseEntity(toDoListService.save(toDoList), HttpStatus.CREATED);
    }
}