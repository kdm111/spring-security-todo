package sesac.springsecuritytodo.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sesac.springsecuritytodo.entity.UserEntity;
import sesac.springsecuritytodo.repository.UserRepository;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public UserEntity createUser(UserEntity userEntity) {
        if (userEntity == null || userEntity.getEmail() == null) {
            throw new RuntimeException("email is required");
        }
        final String email = userEntity.getEmail();
        if (userRepository.existsByEmail(email)) {
            log.warn("이미 {}로 가입된 이메일이 존재함", email);
            throw new RuntimeException("email is duplicated");
        }
        return userRepository.save(userEntity); // userEntity를 DB에 저장
    }
    public UserEntity getByCredentials(final String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }
}
