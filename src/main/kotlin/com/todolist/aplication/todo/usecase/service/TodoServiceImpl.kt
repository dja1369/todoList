package com.todolist.aplication.todo.usecase.service

import com.todolist.aplication.commonRepository.UserRepository
import com.todolist.aplication.todo.repository.TodoRepository
import com.todolist.domain.todo.Todo
import com.todolist.domain.todo.enums.Status
import com.todolist.domain.todo.module.TodoService
import com.todolist.domain.todo.vo.TodoVo
import com.todolist.domain.todo.vo.TodoVoDetail
import com.todolist.domain.todo.vo.TodoVoList
import com.todolist.utils.ConverterUtil
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

private val logger = KotlinLogging.logger {}

@Component
class TodoServiceImpl(
    private val userRepository: UserRepository,
    private val todoRepository: TodoRepository
)
    : TodoService {
    override fun createTodo(title: String, content: String?): TodoVoDetail {
        val todo = Todo(
            title = title,
            content = content,
            user = userRepository.findByIdOrNull(ConverterUtil.jwtIdToUUID()) ?: throw Exception("User Not Found")
        )
        todoRepository.save(todo)
        return todo.toVoDetail()
    }

    override fun updateTodo(id: String, content: String, newStatus: Status): TodoVoDetail {
        val todo = todoRepository.findByUuidId(UUID.fromString(id)) ?: throw Exception("Todo Not Found")
        todo.content = content
        when (todo.status){
            Status.IN_PROGRESS -> {
                check(newStatus == Status.PENDING) { "PENDING은 오직 IN_PROGRESS 상태에서만 변경 가능합니다." }
                todo.status = newStatus
            }
            Status.PENDING -> {
                todo.status = newStatus
            }
            else -> {
                check(newStatus != Status.PENDING) { "PENDING은 오직 IN_PROGRESS 상태에서만 변경 가능합니다." }
                todo.status = newStatus
            }
        }
        todoRepository.save(todo)
        return todo.toVoDetail()
    }

    override fun deleteTodo(id: String): Boolean {
        val todo = todoRepository.findByUuidId(UUID.fromString(id)) ?: throw Exception("Todo Not Found")
        todo.deletedAt = LocalDateTime.now()
        todoRepository.save(todo)
        return true
    }

    override fun getMostRecentTodo(): TodoVo {
        logger.info { "Get Most Recent Todo Start" }
        val todoByUpdatedAt: Todo? = todoRepository.findByUserIdAndDeletedAtIsNullOrderByUpdatedAtDesc(
            ConverterUtil.jwtIdToUUID())
        logger.info { "Get Most Recent Todo By UpdatedAt" }
        val todoByCreatedAt: Todo? = todoRepository.findByUserIdAndDeletedAtIsNullOrderByCreatedAtDesc(
            ConverterUtil.jwtIdToUUID())
        logger.info { "Get Most Recent Todo By CreatedAt" }

        logger.info { "Todo Based On UpdateAt Compare to Todo Based On CreateAt"}
        return when {
            todoByUpdatedAt == null && todoByCreatedAt == null -> throw Exception("Todo Not Found")
            todoByUpdatedAt == null -> todoByCreatedAt!!.toVo()
            todoByCreatedAt == null -> todoByUpdatedAt.toVo()
            todoByUpdatedAt.updatedAt!!.isAfter(todoByCreatedAt.createdAt) -> todoByUpdatedAt.toVo()
            else -> todoByCreatedAt.toVo()
        }
    }

    override fun getAllTodo(): TodoVoList {
        val todos = todoRepository.findAllByUserId(
            ConverterUtil.jwtIdToUUID()) ?: throw Exception("Todo Not Found")
        return TodoVoList(todos.map { it.toVo() })
    }

    override fun getTodoDetail(id: String): TodoVoDetail {
        val todo = todoRepository.findByUuidId(UUID.fromString(id)) ?: throw Exception("Todo Not Found")
        return todo.toVoDetail()
    }
}