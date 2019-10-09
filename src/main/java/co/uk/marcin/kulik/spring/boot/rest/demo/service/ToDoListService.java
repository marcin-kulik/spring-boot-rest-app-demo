package co.uk.marcin.kulik.spring.boot.rest.demo.service;

import co.uk.marcin.kulik.spring.boot.rest.demo.dto.ToDoListRepository;
import co.uk.marcin.kulik.spring.boot.rest.demo.model.ToDoList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Marcin Kulik
 */

@Service
public class ToDoListService {

    @Autowired
    ToDoListRepository toDoListRepository;

    public List<ToDoList> findAll() {
        return toDoListRepository.findAll();
    }

    public Optional<ToDoList> findById(Long toDoListId){
        return toDoListRepository.findById(toDoListId);
    }

    public ToDoList save(ToDoList toDoList) {
        return toDoListRepository.save(toDoList);
    }
}
