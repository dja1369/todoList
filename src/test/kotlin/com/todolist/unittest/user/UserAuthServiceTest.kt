package com.todolist.unittest.user

import com.todolist.aplication.commonRepository.UserRepository
import com.todolist.domain.user.entity.User
import com.todolist.domain.user.module.UserAuthService
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.crypto.password.PasswordEncoder

import java.time.LocalDateTime

@SpringBootTest
class UserAuthServiceTest {
    @MockBean private lateinit var userRepository: UserRepository
    @MockBean private lateinit var passwordEncoder: PasswordEncoder
    @Autowired private lateinit var userAuthService: UserAuthService

    private val email = "test@test.com"
    private val nickName = "test"
    private val password = "password"
    private val wrongPassword = "wrongPassword"

    @Test
    fun `로그인 성공 테스트`(){
        // given
        val user = User(email = email, password = password, nickName = nickName)
        `when`(userRepository.findByEmail(email)).thenReturn(user)
        `when`(passwordEncoder.matches(password, user.password)).thenReturn(true)

        // when
        val result = userAuthService.validateLogin(email, password)

        // then
        assertThat(result).isEqualTo(user)
    }
    @Test
    fun `탈퇴한 유저 로그인 실패 테스트`(){
        // given
        val user = User(email = email, password = password, nickName = nickName)
        user.deletedAt = LocalDateTime.now()
        `when`(userRepository.findByEmail(email)).thenReturn(user)
        `when`(passwordEncoder.matches(password, user.password)).thenReturn(true)

        // when && then
        assertThatThrownBy {
            userAuthService.validateLogin(email, password)
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Deleted User")
    }

    @Test
    fun `비밀번호가 맞지 않는 로그인 실패 테스트`(){
        // given
        val user = User(email = email, password = wrongPassword, nickName = nickName)
        `when`(userRepository.findByEmail(email)).thenReturn(user)
        `when`(passwordEncoder.matches(password, user.password)).thenReturn(false)

        // when && then
        assertThatThrownBy {
            userAuthService.validateLogin(email, password)
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Password Not Match")
    }

    @Test
    fun `이메일 중복이 아닐경우 테스트`(){
        // given
        `when`(userRepository.existsByEmail(email)).thenReturn(false)

        // when
        val result = userAuthService.validateEmail(email)

        // then
        assertThat(result).isFalse()
    }

    @Test
    fun `이메일 중복일 경우 테스트`() {
        // given
        `when`(userRepository.existsByEmail(email)).thenReturn(true)

        // when && then
        assertThatThrownBy {
            userAuthService.validateEmail(email)
        }.isInstanceOf(IllegalStateException::class.java)
            .hasMessage("Email is already in use")
    }

    @Test
    fun `닉네임 중복이 아닐경우 테스트`(){
        // given
        `when`(userRepository.existsByNickName(nickName)).thenReturn(false)

        // when
        val result = userAuthService.validateNickName(nickName)

        // then
        assertThat(result).isFalse()
    }

    @Test
    fun `닉네임 중복일 경우 테스트`() {
        // given
        `when`(userRepository.existsByNickName(nickName)).thenReturn(true)

        // when && then
        assertThatThrownBy {
            userAuthService.validateNickName(nickName)
        }.isInstanceOf(IllegalStateException::class.java)
            .hasMessage("NickName is already in use")
    }

}