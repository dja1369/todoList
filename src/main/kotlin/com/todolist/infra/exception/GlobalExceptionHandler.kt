package com.todolist.infra.exception

import com.todolist.domain.common.CommonResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.View
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler(private val error: View) : ResponseEntityExceptionHandler() {
    @ExceptionHandler(Exception::class)
    fun handleAllException(e: Exception): ResponseEntity<CommonResponse> {
        val errorDetail = CommonResponse(
            status = HttpStatus.OK.value(),
            message = e.message ?: "Error",
            result = emptyArray<Any>()
        )
        return ResponseEntity(errorDetail, HttpStatus.BAD_REQUEST)
    }
}