package rest.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.demo.model.Task;
import rest.demo.repository.TaskRepository;
import rest.demo.repository.ToDoRepository;

import java.util.List;

/**
 * @author Marcin Kulik
 */

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    ToDoRepository toDoRepository;

    public List<Task> findTasks(Long id){
        return toDoRepository.findById(id).get().getListOfTasks();
    }

    public Task create(Long id, Task task){
        task.setToDo(toDoRepository.findById(id).get());
        taskRepository.save(task);
        return task;
    }
}
