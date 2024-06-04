package com.todolist.aplication.user.controller

import com.todolist.aplication.user.dto.request.UserRegisterRequest
import com.todolist.aplication.user.usecase.UserApplication
import com.todolist.domain.common.CommonResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class UserRegisterController(
    private val userApplication: UserApplication
) {
    @PostMapping("/registers/register")
    fun register(@RequestBody request: UserRegisterRequest): ResponseEntity<CommonResponse> {
        val response = userApplication.isRegister(request)
        return ResponseEntity(response, HttpStatus.OK)
    }
}