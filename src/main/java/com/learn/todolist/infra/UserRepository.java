package com.learn.todolist.infra;

import com.learn.todolist.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
//    Optional<User> findByEmailAndDeletedAtIsNull(String email);
    Boolean existsByEmail(String email);
//    Boolean existsByEmailAndDeletedAtIsNull(String email);
    Boolean existsByNickName(String nickName);
//    Boolean existsByNickNameAndDeletedAtIsNull(String nickName);
}
