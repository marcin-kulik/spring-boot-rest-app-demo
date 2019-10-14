package rest.demo.service;

import rest.demo.repository.TaskRepository;
import rest.demo.repository.ToDoRepository;
import rest.demo.model.Task;
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
    ToDoRepository toDoRepository;

    public List<Task> findAllInList(Long toDoListId){
       return toDoRepository.findById(toDoListId).get().getListOfTasks();
    }

    public Task createTask(Long toDoListId, Task task){
        task.setToDo(toDoRepository.findById(toDoListId).get());
        taskRepository.save(task);
        return task;
    }
}
