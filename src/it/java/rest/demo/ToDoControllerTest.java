package rest.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import rest.demo.controller.ToDoController;
import rest.demo.model.ToDo;
import rest.demo.repository.ToDoRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ToDoController.class)
@DisplayName("Given ToDoController and mock ToDoRepository,")
public class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToDoRepository toDoRepository;

    @Test
    @DisplayName("When GET request and no ToDos, Then NOT_FOUND")
    void whenNoToDos_thenNotFound() throws Exception {
        mockMvc.perform(get("/api/todos")
                .contentType("application/json"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("When GET request and ToDos, Then ToDos returned")
    void whenToDos_thenSuccessful() throws Exception {

        ToDo toDo1 = ToDo.builder().name("List 1").description("List 1 description").build();
        ToDo toDo2 = ToDo.builder().name("List 2").description("List 2 description").build();
        List<ToDo> toDos = Arrays.asList(toDo1, toDo2);
        when(toDoRepository.findAll()).thenReturn(toDos);

        mockMvc.perform(get("/api/todos")
                .contentType("application/json"))
                .andExpect(status().is2xxSuccessful());
    }
}
