package sesac.springsecuritytodo.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sesac.springsecuritytodo.dto.ResponseDTO;
import sesac.springsecuritytodo.dto.UserDTO;
import sesac.springsecuritytodo.entity.UserEntity;
import sesac.springsecuritytodo.security.TokenProvider;
import sesac.springsecuritytodo.service.UserService;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            // 1. request body에 담긴 사용자 정보를 entity 객체로 바꿈
            UserEntity userEntity = UserEntity.builder()
                    .email(userDTO.getEmail())
                    .userId(userDTO.getUserId())
                    .password(userDTO.getPassword())
                    .build();
            // 2. 서비스에서 저장
            UserEntity registeredUser = userService.createUser(userEntity);
            // 3.
            UserDTO retDTO = UserDTO.builder()
                    .email(registeredUser.getEmail())
                    .userId(registeredUser.getUserId())
                    .build();
            return ResponseEntity.ok().body(retDTO);
        } catch (Exception e) {
            ResponseDTO resDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(resDTO);
        }
    }
    // 로그인 시 jwt 적용
    @PostMapping("/signin")
    public ResponseEntity<?> signInUser(@RequestBody UserDTO userDTO) {
        UserEntity user = userService.getByCredentials(userDTO.getEmail(), userDTO.getPassword());
        if (user != null) {
            final String token = tokenProvider.createJWT(user);
            final UserDTO resUserDTO = UserDTO.builder()
                    .email(user.getEmail())
                    .userId(user.getUserId())
                    .id(user.getId())
                    .token(token)
                    .build();


            return ResponseEntity.ok().body(resUserDTO);
        } else {
            ResponseDTO resDTO = ResponseDTO.builder().error("not founded").build();
            return ResponseEntity.badRequest().body(resDTO);
        }
    }
}
