package rest.demo;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import rest.demo.controller.ToDoController;
import rest.demo.model.Task;
import rest.demo.model.ToDo;
import rest.demo.repository.ToDoRepository;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Given ToDoController,")
public class ToDoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private ToDoController toDoController;

    private ToDo toDo1;
    private List<ToDo> toDos;

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

        saveToDosInRepository();

        mockMvc.perform(get("/api/todos")
                .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[0].name").value("List 1"))
                .andExpect(jsonPath("$.[1].name").value("List 2"))
                .andExpect(jsonPath("$.[0].description").value("List 1 description"))
                .andExpect(jsonPath("$.[1].description").value("List 2 description"))
                .andExpect(jsonPath("$.[0].tasks.length()").value(0))
                .andExpect(jsonPath("$.[1].tasks.length()").value(0));

        deleteToDosFromRepository();
    }

    @Test
    @DisplayName("When GET request and ToDos have tasks, Then ToDos with Tasks returned")
    void whenToDosHaveTasks_thenSuccessfulAndReturnsToDosWithTasks() throws Exception {

        saveToDosInRepository();

        Task task1 = Task.builder().name("Task 1").description("Task 1 description").build();
        Task task2 = Task.builder().name("Task 2").description("Task 2 description").build();
        List<Task> tasks = Arrays.asList(task1, task2);
        toDo1 = ToDo.builder().tasks(tasks).build();

        mockMvc.perform(get("/api/todos")
                .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[0].name").value("List 1"))
                .andExpect(jsonPath("$.[1].name").value("List 2"))
                .andExpect(jsonPath("$.[0].description").value("List 1 description"))
                .andExpect(jsonPath("$.[1].description").value("List 2 description"))
                .andExpect(jsonPath("$.[0].tasks.[0].name").value("Task 1"))
                .andExpect(jsonPath("$.[0].tasks.[1].name").value("Task 2"));

        deleteToDosFromRepository();
    }

    private void saveToDosInRepository() {
        toDo1 = ToDo.builder().name("List 1").description("List 1 description").build();
        ToDo toDo2 = ToDo.builder().name("List 2").description("List 2 description").build();
        toDos = Arrays.asList(toDo1, toDo2);
        toDoRepository.saveAll(toDos);
    }


    private void deleteToDosFromRepository() {
        toDoRepository.deleteAll(toDos);
    }

}
