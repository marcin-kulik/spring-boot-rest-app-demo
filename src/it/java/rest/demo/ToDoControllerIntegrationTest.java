package rest.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import rest.demo.model.Task;
import rest.demo.model.ToDo;
import rest.demo.repository.ToDoRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Marcin Kulik
 */

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@DisplayName("Given ToDoController,")
public class ToDoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoRepository toDoRepository;

    private ToDo toDo1;
    private List<ToDo> toDos;
    private List<Task> tasks;
    private LocalDate date = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Test
    @DisplayName("When GET and no ToDos, Then NOT_FOUND")
    void whenNoToDos_thenNotFound() throws Exception {

        mockMvc.perform(get("/api/todos")
                .contentType("application/json"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("When GET and ToDos, Then ToDos")
    void whenToDos_thenSuccessfulAndReturnsToDos() throws Exception {

        createToDos();
        toDoRepository.saveAll(toDos);

        mockMvc.perform(get("/api/todos")
                .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].name").value("List 1"))
                .andExpect(jsonPath("$.[0].description").value("List 1 description"))
                .andExpect(jsonPath("$.[0].tasks").isEmpty())
                .andExpect(jsonPath("$.[0].createdAt").value(containsString(date.format(formatter))))
                .andExpect(jsonPath("$.[0].updatedAt").value(containsString(date.format(formatter))))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].name").value("List 2"))
                .andExpect(jsonPath("$.[1].description").value("List 2 description"))
                .andExpect(jsonPath("$.[1].tasks").isEmpty())
                .andExpect(jsonPath("$.[1].createdAt").value(containsString(date.format(formatter))))
                .andExpect(jsonPath("$.[1].updatedAt").value(containsString(date.format(formatter))));

         }

    @Test
    @DisplayName("When GET and ToDos with Tasks, Then ToDos with Tasks")
    void whenToDosHaveTasks_thenSuccessfulAndReturnsToDosWithTasks() throws Exception {

        createToDos();
        createTasks();
        toDo1.setTasks(tasks);
        toDoRepository.saveAll(toDos);

        mockMvc.perform(get("/api/todos")
                .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].name").value("List 1"))
                .andExpect(jsonPath("$.[0].description").value("List 1 description"))
                .andExpect(jsonPath("$.[0].tasks.[0].id").value(1))
                .andExpect(jsonPath("$.[0].tasks.[0].name").value("Task 1"))
                .andExpect(jsonPath("$.[0].tasks.[1].id").value(2))
                .andExpect(jsonPath("$.[0].tasks.[1].name").value("Task 2"))
                .andExpect(jsonPath("$.[0].createdAt").value(containsString(date.format(formatter))))
                .andExpect(jsonPath("$.[0].updatedAt").value(containsString(date.format(formatter))))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].name").value("List 2"))
                .andExpect(jsonPath("$.[1].description").value("List 2 description"))
                .andExpect(jsonPath("$.[1].tasks").isEmpty())
                .andExpect(jsonPath("$.[1].createdAt").value(containsString(date.format(formatter))))
                .andExpect(jsonPath("$.[1].updatedAt").value(containsString(date.format(formatter))));

    }

    @Test
    @DisplayName("When POST with ToDos, Then ToDos")
    public void whenPostingToDos_ThenSuccessfulAndReturnsToDos() throws Exception {

        createToDos();
        createTasks();
        toDo1.setTasks(tasks);

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(toDos);

        mockMvc.perform(post("/api/todos").contentType(
                MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[0].id").value(1))
                .andExpect(jsonPath("$.[0].name").value("List 1"))
                .andExpect(jsonPath("$.[0].description").value("List 1 description"))
                .andExpect(jsonPath("$.[0].tasks.[0].id").value(1))
                .andExpect(jsonPath("$.[0].tasks.[0].name").value("Task 1"))
                .andExpect(jsonPath("$.[0].tasks.[1].id").value(2))
                .andExpect(jsonPath("$.[0].tasks.[1].name").value("Task 2"))
                .andExpect(jsonPath("$.[0].createdAt").value(containsString(date.format(formatter))))
                .andExpect(jsonPath("$.[0].updatedAt").value(containsString(date.format(formatter))))
                .andExpect(jsonPath("$.[1].id").value(2))
                .andExpect(jsonPath("$.[1].name").value("List 2"))
                .andExpect(jsonPath("$.[1].description").value("List 2 description"))
                .andExpect(jsonPath("$.[1].tasks").isEmpty())
                .andExpect(jsonPath("$.[1].createdAt").value(containsString(date.format(formatter))))
                .andExpect(jsonPath("$.[1].updatedAt").value(containsString(date.format(formatter))));
    }

    private void createToDos() {
        toDo1 = ToDo.builder().name("List 1").description("List 1 description").build();
        ToDo toDo2 = ToDo.builder().name("List 2").description("List 2 description").build();
        toDos = Arrays.asList(toDo1, toDo2);
    }

    private void createTasks() {
        Task task1 = Task.builder().name("Task 1").description("Task 1 description").build();
        Task task2 = Task.builder().name("Task 2").description("Task 2 description").build();
        tasks = Arrays.asList(task1, task2);
    }

}
