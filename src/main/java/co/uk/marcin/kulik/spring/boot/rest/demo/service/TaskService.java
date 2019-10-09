package co.uk.marcin.kulik.spring.boot.rest.demo.service;

import co.uk.marcin.kulik.spring.boot.rest.demo.dto.TaskRepository;
import co.uk.marcin.kulik.spring.boot.rest.demo.dto.ToDoListRepository;
import co.uk.marcin.kulik.spring.boot.rest.demo.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Marcin Kulik
 */

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ToDoListRepository toDoListRepository;

    public Optional<Task> findById(Long toDoListId) {

        return taskRepository.findById(toDoListId);
    }

    public List<Task> findAll(){

        return taskRepository.findAll();
    }

    public List<Task> findAllInList(Long toDoListId){

       return toDoListRepository.findById(toDoListId).get().getListOfTasks();
    }

    public Task createTask(Long toDoListId, Task task){

        task.setToDoList(toDoListRepository.findById(toDoListId).get());
        taskRepository.save(task);
        return task;
    }
}
