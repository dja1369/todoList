package com.todolist.aplication.user.usecase.service

import com.todolist.aplication.user.repository.UserRepository
import com.todolist.domain.user.entity.User
import com.todolist.domain.user.module.UserRegisterService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class UserRegisterServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
): UserRegisterService{
    override fun registerUser(email: String, nickName: String, password: String): Boolean {
        val registerUser = User(
            email = email,
            nickName = nickName,
            password = passwordEncoder.encode(password)
        )
        userRepository.save(registerUser)
        return true
    }

    override fun withdrawalUser(email: String, password: String): Boolean {
        val withdrawalUser: User = userRepository.findByEmail(email) ?: throw UsernameNotFoundException("User Not Found")
        withdrawalUser.deletedAt = LocalDateTime.now()
        userRepository.save(withdrawalUser)
        return true
    }

}