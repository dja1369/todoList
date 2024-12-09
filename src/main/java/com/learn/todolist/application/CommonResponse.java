package com.learn.todolist.application;

public record CommonResponse(
        String message,
        Object result
) {
}
