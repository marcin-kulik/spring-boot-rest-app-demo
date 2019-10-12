package co.uk.marcin.kulik.spring.boot.rest.demo.controller;

import co.uk.marcin.kulik.spring.boot.rest.demo.model.ToDo;
import co.uk.marcin.kulik.spring.boot.rest.demo.service.ToDoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ToDoControllerTest {

    private final String URL = "/api/todos";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ToDoService toDoService;

    @Test
    public void getAll() throws Exception {

        ToDo toDo1 = new ToDo().builder().name("ToDo 1").description("ToDo 1 description").build();
        ToDo toDo2 = new ToDo().builder().name("ToDo 2").description("ToDo 2 description").build();
        ToDo toDo3 = new ToDo().builder().name("ToDo 3").description("ToDo 3 description").build();

        List<ToDo> allToDos = Arrays.asList(toDo1, toDo2, toDo3);

        given(toDoService.findAllToDos()).willReturn(allToDos);

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