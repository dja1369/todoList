package com.learn.todolist.api.dto;

import com.learn.todolist.domain.Status;

import java.util.Optional;

public record TodoModifyReq(
        String title,
        Optional<String> content,
        Status status
) { }
