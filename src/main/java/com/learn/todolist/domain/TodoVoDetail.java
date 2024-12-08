package com.learn.todolist.domain;

import java.util.Optional;

public record TodoVoDetail(
        String id,
        String title,
        Optional<String> content,
        Status status
){}

