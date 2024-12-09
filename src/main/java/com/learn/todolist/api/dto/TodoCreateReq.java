package com.learn.todolist.api.dto;

import java.util.Optional;

public record TodoCreateReq(
        String title,
        Optional<String> content
) {
}
