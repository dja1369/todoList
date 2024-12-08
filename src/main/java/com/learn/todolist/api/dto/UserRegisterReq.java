package com.learn.todolist.api.dto;

public record UserRegisterReq(
        String email,
        String password,
        String nickName
) {}
