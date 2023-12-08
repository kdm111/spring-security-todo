package sesac.springsecuritytodo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String token; // jwt 저장 공간
    private Long id;
    private String userId;
    private String email;
    private String password;
}
