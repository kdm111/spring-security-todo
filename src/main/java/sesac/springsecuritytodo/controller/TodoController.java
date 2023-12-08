package sesac.springsecuritytodo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sesac.springsecuritytodo.dto.ResponseDTO;
import sesac.springsecuritytodo.dto.TodoDTO;
import sesac.springsecuritytodo.entity.TodoEntity;
import sesac.springsecuritytodo.service.TodoService;

import java.util.ArrayList;
import java.util.List;

@RestController
// rest controller 임을 명시하는 어노테이션
// http response body에 객체를 json으로 리턴하는 어노테이션
@RequestMapping("todo") // uri에 어노테이션의 값이 리턴하면 컨트롤러로 실행, 컨트롤러의 기본 경로
// 클래스에 사용하면 하위 메서드에 모두 적용
public class TodoController {
    @Autowired
    private TodoService todoService;
    @PostMapping("")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO) {
        try {
            String tempUserId = "tempUser";
            // 1. DTO -> Entity
            TodoEntity todoEntity = TodoDTO.toEntity(todoDTO);

            // 2. todo가 생성 될 때 id가 null로 초기화 한다.
            todoEntity.setUserId(null);

            // 3. user 설정
            todoEntity.setUserId(tempUserId);

            // 4. 서비스 계층을 통해 todoEntity 생성
            List<TodoEntity> todoEntities = todoService.createTodo(todoEntity);

            // 5. 리턴된 entity 배열을 todoDTO로 변환
            List<TodoDTO> dtos = new ArrayList<>();
            for (TodoEntity entity : todoEntities) {
                TodoDTO dto = new TodoDTO(entity);
                dtos.add(dto);
            }
            // 6. 변환된 TodoDTO 배열을 이용해서 ResponseDTO 초기화
            ResponseDTO<TodoDTO> res = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            // 7. ResponseDTO 리턴
            // java 객체를 리턴해버리면 http 응답 제어가 불가능해지므로
            // rest로 보낼 때는 항상 ResponseEntity 타입으로 보내야 한다.
            // 상태코드 응답 본문 등을 설정해서 클라이언트한테 응답 할 수 있어야 한다.
            // .ok() 성공 200, headers 헤더 설정. body 응답 본문 설정
            return ResponseEntity.status(HttpStatus.CREATED).body(res);
        } catch (Exception e) {
            // 예외가 있는 경우에는 DTO 대신 error를 담아서 리턴
            String error = e.getMessage();
            ResponseDTO<TodoDTO> res = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }
    }



    @GetMapping("")
    public ResponseEntity<?> reteriveTodo() {
        try {
            // 사용자 인증이 필요한 로직
            // 사용자 인증이 너무 많아 지면 복잡해 지기 때문에
            String tempUserId = "tempUser";
            // 1. 서비스 계층에서 retrieve를 가져와서 실행
            List<TodoEntity> entities = todoService.retrieve(tempUserId);
            // 2. 리턴된 엔티티 리스트를 todoDTO 배열로 반환

            List<TodoDTO> dtos = new ArrayList<>();
            for (TodoEntity entity : entities) {
                TodoDTO dto = new TodoDTO(entity);
                dtos.add(dto);
            }
            ResponseDTO<TodoDTO> res = ResponseDTO.<TodoDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(res);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> res = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
        }

    }

}
