package rest.demo.controller;

        import org.junit.Before;
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
@DisplayName("Given user wants to call on ToDo Controller")
public class ToDoControllerTest {

    @Mock
    private ToDoRepository toDoService;

    @InjectMocks
    private ToDoController toDoController;

    ToDo toDo1, toDo2, toDo3;
    List<ToDo> toDos;

    @Before
    public void setup() {
        toDo1 = new ToDo().builder().name("List 1").description("List 1 description").build();
        toDo2 = new ToDo().builder().name("List 2").description("List 2 description").build();
        toDo3 = new ToDo().builder().name("List 3").description("List 3 description").build();
        toDos = Arrays.asList(toDo1, toDo2, toDo3);
    }

    @Test
    @DisplayName("when a ToDo list is in repository, then we get ACCEPTED")
    void getAll_Accepted() {
        ResponseEntity<List<ToDo>> expectedResponse = new ResponseEntity(toDos, HttpStatus.ACCEPTED);
        when(toDoService.findAll()).thenReturn(toDos);
        ResponseEntity<List<ToDo>> actualResponse = toDoController.getAll();
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    @DisplayName("when a ToDo list does not exist in repository, then we get NOT_FOUND")
    void getAll_NotFound() {
        ResponseEntity<List<ToDo>> expectedResponse = new ResponseEntity(HttpStatus.NOT_FOUND);
        List<ToDo> emptyList = Arrays.asList();
        when(toDoService.findAll()).thenReturn(emptyList);
        ResponseEntity<List<ToDo>> actualResponse = toDoController.getAll();
        assertEquals(actualResponse, expectedResponse);
    }

    @Test
    @DisplayName("when a correct id is passed, then we get ACCEPTED")
    void get_CorrectId() {
        Long id = 1L;
        toDo1.setId(id);
        ResponseEntity<ToDo> expectedResponse = new ResponseEntity(toDo1, HttpStatus.ACCEPTED);
        when(toDoService.findById(id)).thenReturn(Optional.of(toDo1));
        ResponseEntity<ToDo> actualResponse = toDoController.get(id);
        assertEquals(actualResponse, expectedResponse);
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
    @DisplayName("when a valid list is passed, then we get CREATED")
    void post() {
        ResponseEntity<List<ToDo>> expectedResponse = new ResponseEntity(toDos, HttpStatus.CREATED);
        when(toDoService.saveAll(toDos)).thenReturn(toDos);
        ResponseEntity<List<ToDo>> actualResponse = toDoController.post(toDos);
        assertEquals(actualResponse, expectedResponse);
    }
}