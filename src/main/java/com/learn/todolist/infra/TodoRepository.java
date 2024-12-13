package com.learn.todolist.infra;

import com.learn.todolist.domain.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<List<Todo>> findAllByUserId(UUID user);
}
