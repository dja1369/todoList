package com.learn.todolist.application;

import com.learn.todolist.api.dto.UserLoginReq;
import com.learn.todolist.api.dto.UserRegisterReq;
import com.learn.todolist.domain.User;
import com.learn.todolist.infra.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserApplication {
    private final UserRegisterServiceImpl userRegisterService;
    private final UserAuthServiceImpl userAuthService;
    private final TokenProvider tokenProvider;

    @Transactional
    public CommonResponse processRegister(UserRegisterReq request) {
        userAuthService.validateEmail(request.email());
        userAuthService.validateNickName(request.nickName());
        Boolean result = userRegisterService.registerUser(request.email(), request.nickName(), request.password());

        return new CommonResponse("Registered User", result);
    }

    public CommonResponse processLogin(UserLoginReq request) {
        User user = userAuthService.validateLogin(request.email(), request.password());
        String token = tokenProvider.generateToken(user.getId());
        return new CommonResponse("Login User", token);
    }

    @Transactional
    public CommonResponse processWithdrawal(UserLoginReq request) {
        Boolean result = userRegisterService.withdrawUser(request.email(), request.password());
        return new CommonResponse("Withdrawal User", result);
    }
}
