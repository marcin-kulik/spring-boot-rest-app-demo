package rest.demo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @author Marcin Kulik
 */

@Entity
@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class Task {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotEmpty
        private String name;

        @NotEmpty
        private String description;

        @CreationTimestamp
        private Date createdAt;

        @UpdateTimestamp
        private Date updatedAt;
}
