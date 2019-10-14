package rest.demo.controller;

import rest.demo.model.ToDo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import rest.demo.repository.ToDoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ToDoControllerTest {

    @Mock
    private ToDoRepository toDoService;

    @InjectMocks
    private ToDoController toDoController;

    @Test
    void getAll() {
        ToDo list1 = new ToDo().builder().name("List 1").description("List 1 description").build();
        ToDo list2 = new ToDo().builder().name("List 2").description("List 2 description").build();
        ToDo list3 = new ToDo().builder().name("List 3").description("List 3 description").build();
        List<ToDo> allLists = Arrays.asList(list1, list2, list3);

        ResponseEntity<List<ToDo>> expectedResponse = new ResponseEntity(allLists, HttpStatus.ACCEPTED);

        when(toDoService.findAll()).thenReturn(allLists);
        ResponseEntity<List<ToDo>> actualResponse = toDoController.getAll();

        assertEquals(actualResponse, expectedResponse);





    }

    @Test
    @DisplayName("when a correct id is passed, then we get ACCEPTED")
    void get_CorrectId() {
        Long id = 1L;
        ToDo toDo = new ToDo().builder().name("ToDo 1").description("ToDo 1 description").build();
        toDo.setId(id);

        ResponseEntity<ToDo> expectedResponse = new ResponseEntity(toDo, HttpStatus.ACCEPTED);

        when(toDoService.findById(id)).thenReturn(Optional.of(toDo));

        ResponseEntity<ToDo> actualResp = toDoController.get(id);

        assertEquals(actualResp, expectedResponse);
    }

    @Test
    @DisplayName("when a correct id is passed, then we get NOT_FOUND")
    void get_IncorrectId() {
        Long id = 100L;

        ResponseEntity<ToDo> expectedResponse = new ResponseEntity(HttpStatus.NOT_FOUND);

        when(toDoService.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<ToDo> actualResponse = toDoController.get(id);

        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    void post() {
    }
}