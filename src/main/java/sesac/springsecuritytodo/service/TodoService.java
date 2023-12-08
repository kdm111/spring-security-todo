package sesac.springsecuritytodo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sesac.springsecuritytodo.entity.TodoEntity;
import sesac.springsecuritytodo.repository.TodoRepository;

import java.util.List;

// log를 찍어주는 log 라이브러리
// info, debug, warn, error를 나눠서 찍어 주는 에러
// 로깅을 하는 클래스에 붙여주는 에러
@Slf4j // simple logging facade for java
@Service
public class TodoService {
    @Autowired
    private TodoRepository todoRepository; // 의존성 주입

    // create todo
    public List<TodoEntity> createTodo(final TodoEntity todoEntity)  {
        // 유효성 검사
        validate(todoEntity);

        todoRepository.save(todoEntity);
        log.info("Entity Id : {} todo creatd", todoEntity.getId()); // 정보 출력
        return todoRepository.findByUserId(todoEntity.getUserId());
    }

    public List<TodoEntity> retrieve(final String userId) {
        log.info("try to get {}'s todos", userId);
        List<TodoEntity> list = todoRepository.findByUserId(userId);
        if (list == null) {
            log.warn("{}'s todos not Founded", userId);
        }
        return list;
    }
    // 유효성 검사
    // c,u,d를 할 경우 validate를 사용한다.
    private void validate(final TodoEntity todoEntity) {
        if (todoEntity == null) {
            log.error("entity can't be null");
            throw new RuntimeException("entity can't be null");
        }
        if (todoEntity.getUserId() == null) {
            log.error("userId is required");
            throw new RuntimeException("userId is required");
        }
        if (todoEntity.getTitle() == null) {
            log.error("title is required");
            throw new RuntimeException("title is required");
        }
    }

}
