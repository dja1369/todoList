package com.learn.todolist.api.controller;

import com.learn.todolist.api.dto.UserLoginReq;
import com.learn.todolist.application.CommonResponse;
import com.learn.todolist.application.UserApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class UserAuthController {
    private final UserApplication userApplication;

    @PostMapping("/login")
    public ResponseEntity<CommonResponse> login(@RequestBody UserLoginReq request) {
        CommonResponse response = userApplication.processLogin(request);
        return ResponseEntity.ok(response);
    }
}
