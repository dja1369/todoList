package com.learn.todolist.api.dto;

public record UserLoginReq(
        String email,
        String password
) {}
