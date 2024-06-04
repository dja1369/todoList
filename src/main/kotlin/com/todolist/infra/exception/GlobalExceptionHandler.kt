package com.todolist.infra.exception

import com.todolist.domain.common.CommonResponse
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

private val globalLogger = KotlinLogging.logger {}

@RestControllerAdvice
class GlobalExceptionHandler {
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