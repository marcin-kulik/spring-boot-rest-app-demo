package co.uk.marcin.kulik.spring.boot.rest.demo.service;

import co.uk.marcin.kulik.spring.boot.rest.demo.repository.ToDoRepository;
import co.uk.marcin.kulik.spring.boot.rest.demo.model.ToDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Marcin Kulik
 */

@Service
public class ToDoService {

    @Autowired
    ToDoRepository toDoRepository;

    public List<ToDo> findAllToDos() {
        return toDoRepository.findAll();
    }

    public Optional<ToDo> findToDoById(Long toDoListId){
        return toDoRepository.findById(toDoListId);
    }

    public List<ToDo> saveToDos(List<ToDo> toDoList) {
        return toDoList.stream().map(todo -> toDoRepository.save(todo)).collect(Collectors.toList());
    }
}
