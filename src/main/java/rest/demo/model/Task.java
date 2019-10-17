package rest.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @author Marcin Kulik
 */

@Entity
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Task {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotEmpty
        private String name;

        @NotEmpty
        private String description;

        @ManyToOne
        @JsonIgnore
        private ToDo toDo;

        @CreationTimestamp
        private Date createdAt;

        @UpdateTimestamp
        private Date updatedAt;
}
