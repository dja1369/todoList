package com.todolist.aplication.user.repository

import com.todolist.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository: JpaRepository<User, UUID>{
    fun findByEmail(email: String): User?
    fun findByEmailAndPassword(email: String, password: String): User?
}