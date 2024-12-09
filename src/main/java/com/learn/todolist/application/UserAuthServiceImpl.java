package com.learn.todolist.application;

import com.learn.todolist.domain.User;
import com.learn.todolist.infra.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserAuthServiceImpl {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void validateEmail(String email) {
        Boolean isExistEmail = userRepository.existsByEmail(email);
        if (isExistEmail) throw new IllegalArgumentException("Email is already exist");
    }

    public void validateNickName(String nickName) {
        Boolean isExistNickName = userRepository.existsByNickName(nickName);
        if (isExistNickName) throw new IllegalArgumentException("NickName is already exist");
    }

    public User validateLogin(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Password is not correct");
        }
        return user;
    }
}
