package com.todolist.utils.exception

import com.fasterxml.jackson.databind.exc.InvalidFormatException
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.todolist.domain.common.CommonResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

private val globalLogger = KotlinLogging.logger {}

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<CommonResponse> {
        val error = when (val cause = e.cause) {
            is InvalidFormatException -> {
                val enumValues = cause.targetType.enumConstants.joinToString(separator = ", ") { it.toString() }
                "Not Match Enum class: [$enumValues]"
            }
            is MismatchedInputException -> {
                val missingParameter = cause.path.joinToString(separator = ".") { it.fieldName }
                "Missing parameter: $missingParameter"
            }
            else -> when(val nestCause = cause?.cause){
                is IllegalArgumentException -> {
                    val missingParameter = nestCause.message
                    "Validation Error: $missingParameter"
                }
                else -> "Invalid Error"
            }
        }
        val errorDetail = CommonResponse(
            message = error,
            result = emptyArray<Any>()
        )
        return ResponseEntity(errorDetail, HttpStatus.BAD_REQUEST)
    }
    @ExceptionHandler(Exception::class)
    fun handleAllException(request: HttpServletRequest, e: Exception): ResponseEntity<CommonResponse> {
        globalLogger.error {
            "User: ${SecurityContextHolder.getContext().authentication.principal ?: null}" +
            "   |   URL: ${request.requestURI}" +
            "   |   Method: ${request.method}" +
            "   |   IP: ${getIpAddress(request)}" +
            "   |   Error : ${e.message}" }
        val errorDetail = CommonResponse(
            message = e.message ?: "Error",
            result = emptyArray<Any>()
        )
        return ResponseEntity(errorDetail, HttpStatus.BAD_REQUEST)
    }


    private fun getIpAddress(request: HttpServletRequest): String {
        var ipAddrV6 = request.getHeader("X-Forwarded-For")

        if (ipAddrV6 == null) ipAddrV6 = request.getHeader("Proxy-Client-IP")

        if (ipAddrV6 == null) ipAddrV6 = request.getHeader("WL-Proxy-Client-IP")

        if (ipAddrV6 == null) ipAddrV6 = request.getHeader("HTTP_CLIENT_IP")

        if (ipAddrV6 == null) ipAddrV6 = request.getHeader("HTTP_X_FORWARDED_FOR")

        if (ipAddrV6 == null) ipAddrV6 = request.remoteAddr

        return ipAddrV6
    }
}