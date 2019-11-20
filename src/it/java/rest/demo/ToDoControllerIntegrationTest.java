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
@DisplayName("Given ToDoController is called,")
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
    @DisplayName("When ToDos not retrieved, Then return NOT_FOUND")
    void getToDos_NotFound() throws Exception {

        mockMvc.perform(get("/api/todos")
                .contentType("application/json"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("When ToDos retrieved, Then return ToDos")
    void getToDos_Accepted() throws Exception {

        createToDos();
        toDoRepository.saveAll(toDos);

        mockMvc.perform(get("/api/todos")
                .contentType("application/json"))
                .andExpect(status().isAccepted())
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
    @DisplayName("When ToDos with Tasks retrieved, Then return ToDos with Tasks")
    void getToDos_WithTasks() throws Exception {

        createToDosAndTasks();
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
    @DisplayName("When incorrect Id received, Then return NOT_FOUND")
    void get_IncorrectId() throws Exception{

        mockMvc.perform(get("/api/todos/1")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("When correct Id received, Then return ACCEPTED")
    public void get_CorrectId() throws Exception {

        createToDos();
        toDoRepository.saveAll(toDos);

        mockMvc.perform(get("/api/todos/1")
                .contentType("application/json"))
                .andExpect(status().isAccepted());
    }

    @Test
    @DisplayName("When Tasks not retrieved, Then return empty List")
    void get_CorrectId_EmptyListOfTasks() throws Exception {

        createToDos();
        toDoRepository.saveAll(toDos);

        mockMvc.perform(get("/api/todos/1")
                .contentType("application/json"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("List 1"))
                .andExpect(jsonPath("$.description").value("List 1 description"))
                .andExpect(jsonPath("$.tasks").isEmpty());
    }

    @Test
    @DisplayName("When Tasks retrieved, Then return Tasks")
    void get_CorrectId_ListOfTasks() throws Exception {

        createToDosAndTasks();
        toDoRepository.saveAll(toDos);

        mockMvc.perform(get("/api/todos/1")
                .contentType("application/json"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("List 1"))
                .andExpect(jsonPath("$.description").value("List 1 description"))
                .andExpect(jsonPath("$.tasks").isNotEmpty())
                .andExpect(jsonPath("$.tasks.[0].id").value(1))
                .andExpect(jsonPath("$.tasks.[0].name").value("Task 1"))
                .andExpect(jsonPath("$.tasks.[1].id").value(2))
                .andExpect(jsonPath("$.tasks.[1].name").value("Task 2"));
    }

    @Test
    @DisplayName("When ToDos and Tasks received, Then return ToDos and Tasks")
    public void post_ToDosAndTasks() throws Exception {

        createToDosAndTasks();
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

    private void createToDosAndTasks() {
        createToDos();
        createTasks();
        toDo1.setTasks(tasks);
    }

}
