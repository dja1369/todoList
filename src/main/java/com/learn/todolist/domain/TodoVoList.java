package com.learn.todolist.domain;

import java.util.Collection;

public record TodoVoList(
        Collection<TodoVo> todoList
){}
