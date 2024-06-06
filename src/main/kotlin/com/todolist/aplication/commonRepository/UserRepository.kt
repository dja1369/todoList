package com.todolist.aplication.commonRepository

import com.todolist.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository: JpaRepository<User, UUID>{
    fun findByEmailAndDeletedAtIsNull(email: String): User?
    fun existsByEmailAndDeletedAtIsNull(email: String): Boolean
    fun existsByNickNameAndDeletedAtIsNull(nickName: String): Boolean
}