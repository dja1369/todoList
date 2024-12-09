package com.learn.todolist.api.controller;

import com.learn.todolist.api.dto.TodoCreateReq;
import com.learn.todolist.api.dto.TodoModifyReq;
import com.learn.todolist.application.CommonResponse;
import com.learn.todolist.application.TodoApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {
    private final TodoApplication todoApplication;

    @GetMapping
    public ResponseEntity<CommonResponse> getTodoList() {
        CommonResponse response = todoApplication.processGetTodoList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/todo")
    public ResponseEntity<CommonResponse> createTodo(@RequestBody TodoCreateReq request) {
        CommonResponse response = todoApplication.processCreateTodo(request);
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/todo/{id}")
    public ResponseEntity<CommonResponse> updateTodo(@PathVariable Long id, @RequestBody TodoModifyReq request) {
        CommonResponse response = todoApplication.processUpdateTodo(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<CommonResponse> deleteTodo(@PathVariable Long id) {
        CommonResponse response = todoApplication.processDeleteTodo(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<CommonResponse> getTodoDetail(@PathVariable Long id) {
        CommonResponse response = todoApplication.processGetTodoDetail(id);
        return ResponseEntity.ok(response);
    }

}
