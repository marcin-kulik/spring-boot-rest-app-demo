package rest.demo.controller;

import rest.demo.model.ToDo;
import rest.demo.repository.ToDoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ToDoControllerIntegrationTest {

    private final String URL = "/api/todos";

    @Autowired
    ToDoController toDoController;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ToDoRepository toDoRepository;
    ToDo toDo1, toDo2,toDo3;


    @Before
    public void setup(){
        ToDo toDo1 = new ToDo().builder().name("ToDo 1").description("ToDo 1 description").build();
        ToDo toDo2 = new ToDo().builder().name("ToDo 2").description("ToDo 2 description").build();
        ToDo toDo3 = new ToDo().builder().name("ToDo 3").description("ToDo 3 description").build();
        List<ToDo> allToDos = Arrays.asList(toDo1, toDo2, toDo3);
        toDoRepository.saveAll(allToDos);
    }
//    TODO fix java.lang.NullPointerException line 57
    @Test
    public void getAll() throws Exception {

        mockMvc.perform(get(URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is(toDo1.getName())))
                .andExpect(jsonPath("$[1].name", is(toDo2.getName())))
                .andExpect(jsonPath("$[2].name", is(toDo3.getName())))
                .andExpect(jsonPath("$[0].description", is(toDo1.getDescription())))
                .andExpect(jsonPath("$[1].description", is(toDo2.getDescription())))
                .andExpect(jsonPath("$[2].description", is(toDo3.getDescription())));
    }
}