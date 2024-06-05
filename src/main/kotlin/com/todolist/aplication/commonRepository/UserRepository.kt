package com.todolist.aplication.commonRepository

import com.todolist.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository: JpaRepository<User, UUID>{
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
    fun existsByNickName(nickName: String): Boolean
}