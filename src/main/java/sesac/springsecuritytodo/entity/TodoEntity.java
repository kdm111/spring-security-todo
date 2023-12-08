package sesac.springsecuritytodo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="todo")
public class TodoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long id;
    @Column(name="userId", updatable = false, nullable = false)
    private String userId;
    @Column(name="title",  nullable = false)
    private String title;
    @Column(name="done",  nullable = false)
    private boolean done;
}
