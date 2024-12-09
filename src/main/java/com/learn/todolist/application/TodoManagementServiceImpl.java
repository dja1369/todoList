package com.learn.todolist.application;

import com.learn.todolist.domain.Status;
import com.learn.todolist.domain.Todo;
import com.learn.todolist.infra.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class TodoManagementServiceImpl {
    private final TodoRepository todoRepository;

    public boolean createTodo(UUID userId, String title, Optional<String> content) {
        Todo todo = Todo.builder()
                .title(title)
                .content(content.orElse(null))
                .user(userId) // TODO: 스프링 컨텍스트 홀더에서 가져와서 사용하도록 수정 필요
                .build();
        todoRepository.save(todo);
        return true;
    }

    public boolean updateTodo(Long id, String title, Optional<String> content, Status status) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Todo not found"));
        todo.setTitle(title);
        content.ifPresent(todo::setContent);
        todo.setStatus(status);
        todoRepository.save(todo);
        return true;
    }

    public boolean deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Todo not found"));
        todo.setDeletedAt(LocalDateTime.now());
        todoRepository.save(todo);
        return true;
    }

    public Todo getTodoDetail(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Todo not found"));
        return todo;
    }

    public List<Todo> getUserTodoList(UUID userId){
        List<Todo> todoList = todoRepository.findAllByUserId(userId).orElseThrow(() -> new IllegalArgumentException("Todo not found"));
        return todoList;
    }
}
