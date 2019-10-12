package co.uk.marcin.kulik.spring.boot.rest.demo.service;

import co.uk.marcin.kulik.spring.boot.rest.demo.model.ToDo;
import co.uk.marcin.kulik.spring.boot.rest.demo.repository.ToDoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ToDoServiceTest {

    @TestConfiguration
    static class ToDoListServiceTestContextConfiguration {

        @Bean
        public ToDoService toDoListService() {
            return new ToDoService();
        }
    }

    //    Why @Autowired and not @Mock?
    @Autowired
    private ToDoService toDoListService;

    //    Can I create 2 mocks of the same repository to test for nulls as well and have them all in set up method?
    @MockBean
    private ToDoRepository toDoListRepository;

    @Before
    public void setUp() {
        ToDo toDo1 = new ToDo().builder().name("ToDo 1").description("ToDo 1 description").build();
        ToDo toDo2 = new ToDo().builder().name("ToDo 2").description("ToDo 2 description").build();
        ToDo toDo3 = new ToDo().builder().name("ToDo 3").description("ToDo 3 description").build();

//        Should I return an optional?
        Mockito.when(toDoListRepository.findById(toDo1.getId()))
                .thenReturn(Optional.of(toDo1));
        Mockito.when(toDoListRepository.findById(toDo2.getId()))
                .thenReturn(Optional.of(toDo2));
        Mockito.when(toDoListRepository.findById(toDo3.getId()))
                .thenReturn(Optional.of(toDo3));
    }

    @Test
    public void whenValidId_thenToDoListShouldBeFound() {
        Long id1 = 1L;
        Long id2 = 2L;
        Long id3 = 3L;

        // Should I do ToDoList or Optional<ToDoList>?
        ToDo toDo1Found = toDoListService.findToDoById(id1);
        ToDo toDo2Found = toDoListService.findToDoById(id2);
        ToDo toDo3Found = toDoListService.findToDoById(id3);

        assertThat(toDo1Found.getId())
                .isEqualTo(id1);
        assertThat(toDo2Found.getId())
                .isEqualTo(id2);
        assertThat(toDo3Found.getId())
                .isEqualTo(id3);

    }


}