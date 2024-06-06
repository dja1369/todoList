package com.todolist.aplication.todo.repository

import com.todolist.domain.todo.Todo
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface TodoRepository: JpaRepository<Todo, Long>{
    fun findAllByUserIdAndDeletedAtIsNull(userId: UUID): List<Todo>?
    fun findByUuidIdAndDeletedAtIsNull(id: UUID): Todo?
    fun findByUserIdAndDeletedAtIsNullOrderByUpdatedAtDesc(userId: UUID): Todo?
    fun findByUserIdAndDeletedAtIsNullOrderByCreatedAtDesc(userId: UUID): Todo?
}