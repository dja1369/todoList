package com.todolist.aplication.user.usecase.service

import com.todolist.aplication.user.repository.UserRepository
import com.todolist.domain.user.entity.User
import com.todolist.domain.user.module.UserAuthService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.security.crypto.password.PasswordEncoder

@Component
class UserAuthServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
): UserAuthService{
    override fun validateEmail(email: String): Boolean {
        val isExistEmail = userRepository.existsByEmail(email)
        check(!isExistEmail) { "Email is already in use" }
        return isExistEmail
    }

    override fun validateNickName(nickName: String): Boolean {
        val isExistNickName = userRepository.existsByNickName(nickName)
        check(!isExistNickName) { "NickName is already in use" }
        return isExistNickName
    }

    override fun validateLogin(email: String, password: String): User {
        val user: User = userRepository.findByEmail(email) ?: throw UsernameNotFoundException("User Not Found")
        when {
            !passwordEncoder.matches(password, user.password) -> {
                throw IllegalArgumentException("Password Not Match")
            }
            user.deletedAt != null -> {
                throw IllegalArgumentException("Deleted User")
            }
        }
        return user
    }
}