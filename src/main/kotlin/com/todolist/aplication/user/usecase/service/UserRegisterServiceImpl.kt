package com.todolist.aplication.user.usecase.service

import com.todolist.domain.user.module.UserRegisterService
import org.springframework.stereotype.Component

@Component
class UserRegisterServiceImpl(

): UserRegisterService{
    override fun registerUser(email: String, nickName: String, password: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun withdrawalUser(email: String, password: String): Boolean {
        TODO("Not yet implemented")
    }

}