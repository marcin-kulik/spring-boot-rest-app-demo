package rest.demo.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rest.demo.model.Task;
import rest.demo.model.ToDo;
import rest.demo.repository.ToDoRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

/**
 * @author Marcin Kulik
 */

@ExtendWith(MockitoExtension.class)
@DisplayName("Given ToDoController,")
public class ToDoControllerTest {

    @Mock
    private ToDoRepository toDoRepository;

    @InjectMocks
    private ToDoController toDoController;

    private ToDo toDo1;

    private List<ToDo> toDos;

    @BeforeEach
    public void setup() {
        toDo1 = ToDo.builder().name("List 1").description("List 1 description").build();
        ToDo toDo2 = ToDo.builder().name("List 2").description("List 2 description").build();
        ToDo toDo3 = ToDo.builder().name("List 3").description("List 3 description").build();
        toDos = Arrays.asList(toDo1, toDo2, toDo3);
    }

    @Test
    @DisplayName("When ToDos, Then ACCEPTED")
    void getToDos_Accepted() {
        ResponseEntity<List<ToDo>> expectedResponse = new ResponseEntity<>(toDos, HttpStatus.ACCEPTED);
        when(toDoRepository.findAll()).thenReturn(toDos);
        ResponseEntity<List<ToDo>> actualResponse = toDoController.getToDos();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("When no ToDos, Then NOT_FOUND")
    void getAll_NotFound() {
        ResponseEntity<List<ToDo>> expectedResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        List<ToDo> emptyList = Collections.emptyList();
        when(toDoRepository.findAll()).thenReturn(emptyList);
        ResponseEntity<List<ToDo>> actualResponse = toDoController.getToDos();
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("When correct id, Then ACCEPTED")
    void get_withCorrectId() {
        setToDo1IdTo_1L();
        ResponseEntity<ToDo> expectedResponse = new ResponseEntity<>(toDo1, HttpStatus.ACCEPTED);
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(toDo1));
        ResponseEntity<ToDo> actualResponse = toDoController.get(1L);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("When no tasks, Then empty list")
    void get_withCorrectId_andEmptyListOfTasks() {
        setToDo1IdTo_1L();
        List<Task> emptyList = Collections.emptyList();
        toDo1.setTasks(emptyList);
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(toDo1));
        ResponseEntity<ToDo> controllerResponse = toDoController.get(1L);
        List<Task> actualList = Objects.requireNonNull(controllerResponse.getBody()).getTasks();
        assertEquals(emptyList, actualList);
    }

    @Test
    @DisplayName("When Tasks, Then Tasks")
    void get_withCorrectId_andListOfTasks() {
        setToDo1IdTo_1L();
        toDo1.setTasks(returnListOfTasks());
        when(toDoRepository.findById(1L)).thenReturn(Optional.of(toDo1));
        ResponseEntity<ToDo> controllerResponse = toDoController.get(1L);
        List<Task> actualList = Objects.requireNonNull(controllerResponse.getBody()).getTasks();
        assertEquals(returnListOfTasks(), actualList);
    }

    @Test
    @DisplayName("When incorrect id, Then NOT_FOUND")
    void get_withIncorrectId() {
        Long id = 100L;
        ResponseEntity<ToDo> expectedResponse = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        when(toDoRepository.findById(id)).thenReturn(Optional.empty());
        ResponseEntity<ToDo> actualResponse = toDoController.get(id);
        assertEquals(expectedResponse, actualResponse);
    }

    @Test
    @DisplayName("When valid ToDo, then CREATED")
    void post() {
        ResponseEntity<List<ToDo>> expectedResponse = new ResponseEntity<>(toDos, HttpStatus.CREATED);
        when(toDoRepository.saveAll(toDos)).thenReturn(toDos);
        ResponseEntity<List<ToDo>> actualResponse = toDoController.post(toDos);
        assertEquals(expectedResponse, actualResponse);
    }

    private void setToDo1IdTo_1L() {
        Long id = 1L;
        toDo1.setId(id);
    }

    private List<Task> returnListOfTasks(){
        Task task1 = Task.builder().name("Task 1").description("Task 1 description").build();
        Task task2 = Task.builder().name("Task 2").description("Task 2 description").build();
        return Arrays.asList(task1, task2);
    }
}