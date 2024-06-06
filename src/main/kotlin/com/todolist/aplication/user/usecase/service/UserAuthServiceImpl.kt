package com.todolist.aplication.user.usecase.service

import com.todolist.aplication.commonRepository.UserRepository
import com.todolist.domain.user.entity.User
import com.todolist.domain.user.module.UserAuthService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import org.springframework.security.crypto.password.PasswordEncoder

private val logger = KotlinLogging.logger {}

@Component
class UserAuthServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
): UserAuthService{
    override fun validateEmail(email: String): Boolean {
        logger.info { "Check Is Exist Email Check" }
        val isExistEmail = userRepository.existsByEmail(email)
        check(!isExistEmail) { "Email is already in use" }
        logger.info { "Unused Email Then Confirmed" }
        return isExistEmail
    }

    override fun validateNickName(nickName: String): Boolean {
        logger.info { "Check Is Exist NickName Check" }
        val isExistNickName = userRepository.existsByNickName(nickName)
        check(!isExistNickName) { "NickName is already in use" }
        logger.info { "Unused NickName Then Confirmed" }
        return isExistNickName
    }

    override fun validateLogin(email: String, password: String): User {
        logger.info { "Check Is Exist User Check" }
        val user: User = userRepository.findByEmail(email) ?: throw UsernameNotFoundException("User Not Found")
        logger.info { "Exist User Then Confirmed" }
        logger.info { "Check Password Match && Check If Not Withdrawal User" }
        when {
            !passwordEncoder.matches(password, user.password) -> {
                throw IllegalArgumentException("Password Not Match")
            }
            user.deletedAt != null -> {
                throw IllegalArgumentException("Deleted User")
            }
        }
        logger.info { "User Login Confirmed" }
        return user
    }
}