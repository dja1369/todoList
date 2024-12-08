package com.learn.todolist.api.controller;

import com.learn.todolist.api.dto.UserLoginReq;
import com.learn.todolist.api.dto.UserRegisterReq;
import com.learn.todolist.application.CommonResponse;
import com.learn.todolist.application.UserApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/registers")
public class UserRegisterController {
    private final UserApplication userApplication;

    @PostMapping("/register")
    public ResponseEntity<CommonResponse> register(@RequestBody UserRegisterReq request) {
        CommonResponse response = userApplication.processRegister(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/withdraw")
    public ResponseEntity<CommonResponse> withdrawal(@RequestBody UserLoginReq request) {
        CommonResponse response = userApplication.processWithdrawal(request);
        return ResponseEntity.ok(response);
    }
}
