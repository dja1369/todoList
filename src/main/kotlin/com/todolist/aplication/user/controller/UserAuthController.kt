package com.todolist.aplication.user.controller

import com.todolist.aplication.user.dto.request.UserLoginRequest
import com.todolist.aplication.user.usecase.UserApplication
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController("/api/v1")
class UserAuthController(
    private val userApplication: UserApplication
) {
    @PostMapping("/login")
    fun login(@RequestBody request: UserLoginRequest): ResponseEntity<String> {
        return ResponseEntity.ok().body(userApplication.isLogin(request))
    }
}