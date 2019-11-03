package rest.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rest.demo.controller.ToDoController;
import rest.demo.model.ToDo;
import rest.demo.repository.ToDoRepository;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    void whenToDos_thenSuccessfulAndReturnsToDos() throws Exception {

        ToDo toDo1 = ToDo.builder().name("List 1").description("List 1 description").build();
        ToDo toDo2 = ToDo.builder().name("List 2").description("List 2 description").build();
        List<ToDo> toDos = Arrays.asList(toDo1, toDo2);
        when(toDoRepository.findAll()).thenReturn(toDos);

        mockMvc.perform(get("/api/todos")
                .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[0].name").value("List 1"))
                .andExpect(jsonPath("$.[1].name").value("List 2"))
                .andExpect(jsonPath("$.[0].description").value("List 1 description"))
                .andExpect(jsonPath("$.[1].description").value("List 2 description"));
    }
}
