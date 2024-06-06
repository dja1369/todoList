package com.todolist.aplication.user.usecase.service

import com.todolist.aplication.commonRepository.UserRepository
import com.todolist.domain.user.entity.User
import com.todolist.domain.user.module.UserRegisterService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}

@Component
class UserRegisterServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
): UserRegisterService{
    override fun registerUser(email: String, nickName: String, password: String): Boolean {
        logger.info { "User Entity Create" }
        val registerUser = User(
            email = email,
            nickName = nickName,
            password = passwordEncoder.encode(password)
        )

        userRepository.save(registerUser)
        logger.info { "Successful User Signup Welcome $nickName !" }
        return true
    }

    override fun withdrawalUser(email: String, password: String): Boolean {
        logger.info { "Search Withdrawal Target User Entity" }
        val withdrawalUser: User = userRepository.findByEmail(email) ?: throw UsernameNotFoundException("User Not Found")

        logger.info { "Deleted DateTime Update Delete User Entity" }
        withdrawalUser.deletedAt = LocalDateTime.now()
        userRepository.save(withdrawalUser)
        logger.info { "Successful User Withdrawal GoodBye: ${withdrawalUser.nickName}" }
        return true
    }

}