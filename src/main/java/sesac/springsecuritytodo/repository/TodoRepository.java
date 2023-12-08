package sesac.springsecuritytodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sesac.springsecuritytodo.entity.TodoEntity;

import java.util.List;

// TodoRepository 인터페이스 - JpaRepository를 확장
// TodoEntity의 의미는 todo테이블에 매핑될 클래스를 의미하고 Long은 pk 타입을 의미한다.
@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, Long> {

    List<TodoEntity> findByUserId(String userId);
}
