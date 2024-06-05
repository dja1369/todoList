package com.todolist.aplication.todo.usecase

import com.todolist.aplication.todo.dto.request.TodoCreateRequest
import com.todolist.aplication.todo.dto.request.TodoUpdateRequest
import com.todolist.domain.common.CommonResponse
import com.todolist.domain.todo.module.TodoService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

private val logger = KotlinLogging.logger {}

@Service
class TodoApplication(
    private val todoService: TodoService
) {
    @Transactional
    fun processCreateTodo(request: TodoCreateRequest): CommonResponse {
        logger.info { "Create Todo Start" }
        val todo = todoService.createTodo(title = request.title, content = request.content)
        logger.info { "Create Todo Done" }
        return CommonResponse(
            message = "할일 생성 성공",
            result = todo
        )
    }

    @Transactional
    fun processUpdateTodo(request: TodoUpdateRequest): CommonResponse {
        logger.info { "Update Todo Start" }
        val todo = todoService.updateTodo(id = request.id, content = request.content, newStatus = request.status)
        logger.info { "Update Todo Done" }
        return CommonResponse(
            message = "할일 수정 성공",
            result = todo
        )
    }

    @Transactional
    fun processDeleteTodo(id: String): CommonResponse {
        logger.info { "Delete Todo Start" }
        val todo = todoService.deleteTodo(id=id)
        logger.info { "Delete Todo Done" }
        return CommonResponse(
            message = "할일 삭제 성공",
            result = todo
        )
    }

    fun processGetRecentTodo(): CommonResponse {
        logger.info { "Get Recent Todo Start" }
        val todo = todoService.getMostRecentTodo()
        logger.info { "Get Recent Todo Done" }
        return CommonResponse(
            message = "가장 최근 할일 조회 성공",
            result = todo
        )
    }

    fun processGetTodoDetail(id: String): CommonResponse {
        logger.info { "Get Todo Detail Start" }
        val todo = todoService.getTodoDetail(id = id)
        logger.info { "Get Todo Detail Done" }
        return CommonResponse(
            message = "할일 상세 조회 성공",
            result = todo
        )
    }

    fun processGetAllTodo(): CommonResponse {
        logger.info { "Get All Todo Start" }
        val todo = todoService.getAllTodo()
        logger.info { "Get All Todo Done" }
        return CommonResponse(
            message = "할일 전체 조회 성공",
            result = todo
        )

    }
}