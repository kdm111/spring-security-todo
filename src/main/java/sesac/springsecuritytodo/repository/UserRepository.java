package sesac.springsecuritytodo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sesac.springsecuritytodo.entity.UserEntity;


import java.util.Optional;

public interface UserRepository  extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    boolean existsByEmail(String email);
    UserEntity findByEmailAndPassword(String email, String password);
}
