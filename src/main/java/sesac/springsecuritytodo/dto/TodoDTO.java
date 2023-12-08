package sesac.springsecuritytodo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sesac.springsecuritytodo.entity.TodoEntity;

@Builder // 빌더 패턴 자동으로 생성
@NoArgsConstructor // 매개 변수가 없는 기본 생성자를 자동으로 생성
@AllArgsConstructor // 모든 필드를 변수로 받는 생성자를 자동으로 생성
@Data // toString, equals, Getter and Setter
public class TodoDTO {
    private Long id; // 실제 서비스에서는 long으로 사용하는 경우가 많다.
    private String title;
    private boolean done;

    public static TodoEntity toEntity (final TodoDTO todoDTO) {
        return TodoEntity.builder()
                .id(todoDTO.getId())
                .title(todoDTO.getTitle())
                .done(todoDTO.isDone()) // boolean은 is
                .build();
    }
    public TodoDTO(final TodoEntity todoEntity) {
        this.id = todoEntity.getId();
        this.title = todoEntity.getTitle();
        this.done = todoEntity.isDone();
        // userId는 숨김 처리
    }
}
