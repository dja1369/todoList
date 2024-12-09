package com.learn.todolist.application;

import com.learn.todolist.domain.User;
import com.learn.todolist.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class UserRegisterServiceImpl {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Boolean registerUser(String email, String nickName, String password) {
        User user = User.builder().email(email).nickName(nickName).password(passwordEncoder.encode(password)).build();
        userRepository.save(user);
        return true;
    }

    public Boolean withdrawUser(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Password is not correct");
        }
        user.setDeletedAt(LocalDateTime.now());
        userRepository.save(user);
        return true;
    }
}
