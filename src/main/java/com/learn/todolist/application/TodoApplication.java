package com.learn.todolist.application;

import com.learn.todolist.api.dto.TodoCreateReq;
import com.learn.todolist.api.dto.TodoModifyReq;
import com.learn.todolist.domain.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TodoApplication {

    private final TodoManagementServiceImpl todoManagementService;

    @Transactional
    public CommonResponse processCreateTodo(TodoCreateReq request) {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        boolean result= todoManagementService.createTodo(userId, request.title(), request.content());
        return new CommonResponse("Todo Created", result);
    }

    @Transactional
    public CommonResponse processUpdateTodo(Long id, TodoModifyReq request) {
        boolean result = todoManagementService.updateTodo(id, request.title(), request.content(), request.status());
        return new CommonResponse("Todo Updated", result);
    }

    @Transactional
    public CommonResponse processDeleteTodo(Long id) {
        boolean result = todoManagementService.deleteTodo(id);
        return new CommonResponse("Todo Deleted", result);
    }

    public CommonResponse processGetTodoDetail(Long id) {
        Todo todo = todoManagementService.getTodoDetail(id);
        return new CommonResponse("Todo Detail", todo);
    }

    public CommonResponse processGetTodoList() {
        UUID userId = UUID.fromString(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        Collection<Todo> todoList = todoManagementService.getUserTodoList(userId);
        return new CommonResponse("Todo List", todoList);
    }
}
