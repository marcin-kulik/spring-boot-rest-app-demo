package rest.demo.controller;


import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rest.demo.model.ToDo;
import rest.demo.repository.ToDoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Given user wants to call on ToDo Controller")
public class ToDoControllerTest {

    @Mock
    private ToDoRepository toDoRepository;

    @InjectMocks
    private ToDoController toDoController;

    private ToDo toDo1, toDo2, toDo3;
    private List<ToDo> toDos;

    @Before
    public void setup() {
        toDo1 = new ToDo().builder().name("List 1").description("List 1 description").build();
        toDo2 = new ToDo().builder().name("List 2").description("List 2 description").build();
        toDo3 = new ToDo().builder().name("List 3").description("List 3 description").build();
        toDos = Arrays.asList(toDo1, toDo2, toDo3);
    }

    @Test
    @DisplayName("when ToDos are in repository, then we get ACCEPTED")
    void getAll_Accepted() {

        ResponseEntity<List<ToDo>> expectedResponse = new ResponseEntity(toDos, HttpStatus.ACCEPTED);
        when(toDoRepository.findAll()).thenReturn(toDos);
        ResponseEntity<List<ToDo>> actualResponse = toDoController.getToDos();
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    @DisplayName("when ToDos do not exist in repository, then we get NOT_FOUND")
    void getAll_NotFound() {
        ResponseEntity<List<ToDo>> expectedResponse = new ResponseEntity(HttpStatus.NOT_FOUND);
        List<ToDo> emptyList = Arrays.asList();
        when(toDoRepository.findAll()).thenReturn(emptyList);
        ResponseEntity<List<ToDo>> actualResponse = toDoController.getToDos();
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    @DisplayName("when a correct ToDo id is passed, then we get ACCEPTED")
    void get_CorrectId() {
        Long id = 1L;
        toDo1.setId(id);
        ResponseEntity<ToDo> expectedResponse = new ResponseEntity(toDo1, HttpStatus.ACCEPTED);
        when(toDoRepository.findById(id)).thenReturn(Optional.of(toDo1));
        ResponseEntity<ToDo> actualResponse = toDoController.get(id);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    @DisplayName("when an incorrect ToDo id is passed, then we get NOT_FOUND")
    void get_IncorrectId() {
        Long id = 100L;
        ResponseEntity<ToDo> expectedResponse = new ResponseEntity(HttpStatus.NOT_FOUND);
        when(toDoRepository.findById(id)).thenReturn(Optional.empty());
        ResponseEntity<ToDo> actualResponse = toDoController.get(id);
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    @DisplayName("when a valid ToDo is passed, then we get CREATED")
    void post() {
        ResponseEntity<List<ToDo>> expectedResponse = new ResponseEntity(toDos, HttpStatus.CREATED);
        when(toDoRepository.saveAll(toDos)).thenReturn(toDos);
        ResponseEntity<List<ToDo>> actualResponse = toDoController.post(toDos);
        assertEquals(actualResponse, expectedResponse);
    }

    //  TODO - failing test, to be fixed
//    @Test
//    @DisplayName("when a valid toDo is passed and it has tasks, then we get ACCEPTED")
//    void getTasks_la(){
//        Long id = 1L;
//        toDo1.setId(id);
//        Task task1 = new Task().builder().name("Task 1").description("Description 1").build();
//        Task task2 = new Task().builder().name("Task 2").description("Description 2").build();
//        Task task3 = new Task().builder().name("Task 3").description("Description 3").build();
//        List<Task> tasks = Arrays.asList(task1, task2, task3);
//        ResponseEntity<List<Task>> expectedResponse = new ResponseEntity(tasks, HttpStatus.CREATED);
//        when(taskService.findTasks(1L)).thenReturn(tasks);
//        ResponseEntity<List<Task>> actualResponse = toDoController.getTasks(1L);
//        assertEquals(actualResponse, expectedResponse);
//    }

    @Test
    @DisplayName("when a valid toDo and task are passed, then we get ACCEPTED")
    void postTask(){
        // TODO
    }
}