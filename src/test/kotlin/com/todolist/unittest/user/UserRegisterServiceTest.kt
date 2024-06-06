package com.todolist.unittest.user

import com.todolist.aplication.commonRepository.UserRepository
import com.todolist.domain.user.entity.User
import com.todolist.domain.user.module.UserRegisterService
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
class UserRegisterServiceTest {
    @MockBean
    private lateinit var userRepository: UserRepository

    @MockBean
    private lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var userRegisterService: UserRegisterService

    @Test
    fun `회원가입 성공 테스트`() {
        // given
        val email = "test@test.com"
        val nickName = "test"
        val password = "password"
        val encodedPassword = "encodedPassword"
        `when`(passwordEncoder.encode(password)).thenReturn(encodedPassword)

        // when
        val result = userRegisterService.registerUser(email, nickName, password)

        // then
        assertThat(result).isTrue()
    }

    @Test
    fun `이메일이 중복된 회원가입 실패 테스트`() {
        // given
        val email = "test@test.com"
        val nickName = "test"
        val password = "password"
        val user = User(email = email, password = password, nickName = nickName)
        `when`(userRepository.existsByEmail(email)).thenReturn(true)

        // when && then
        assertThatThrownBy {
            userRegisterService.registerUser(email, nickName, password)
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Email is already in use")
    }

    @Test
    fun `닉네임이 중복된 회원가입 실패 테스트`() {
        // given
        val email = "test@test.com"
        val nickName = "test"
        val password = "password"
        val user = User(email = email, password = password, nickName = nickName)
        `when`(userRepository.existsByNickName(nickName)).thenReturn(true)

        // when && then
        assertThatThrownBy {
            userRegisterService.registerUser(email, nickName, password)
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("NickName is already in use")
    }

    @Test
    fun `회원탈퇴 성공 테스트`() {
        // given
        val email = "test@test.com"
        val nickName = "test"
        val password = "password"
        val user = User(email = email, password = password, nickName = nickName)

        `when`(userRepository.findByEmail(email)).thenReturn(user)
        `when`(userRepository.save(any(User::class.java))).thenAnswer { it.getArgument(0) }

        // when
        val result = userRegisterService.withdrawalUser(email, password)

        // then
        assertThat(result).isTrue()
        assertThat(user.deletedAt).isNotNull()
    }

    @Test
    fun `이메일이 맞지 않는 회원탈퇴 실패 테스트`() {
        // given
        val email = "test@test.com"
        val password = "password"
        `when`(userRepository.findByEmail(email)).thenReturn(null)

        // when && then
        assertThatThrownBy {
            userRegisterService.withdrawalUser(email, password)
        }.isInstanceOf(UsernameNotFoundException::class.java)
            .hasMessage("User Not Found")
    }

    @Test
    fun `비밀번호가 맞지 않는 회원탈퇴 실패 테스트`() {
        // given
        val email = "test@test.com"
        val password = "password"
        val wrongPassword = "wrongPassword"
        val nickName = "test"
        val user = User(email = email, password = wrongPassword, nickName = nickName)
        `when`(userRepository.findByEmail(email)).thenReturn(user)
        `when`(passwordEncoder.matches(password, user.password)).thenReturn(false)

        // when && then
        assertThatThrownBy {
            userRegisterService.withdrawalUser(email, password)
        }.isInstanceOf(IllegalArgumentException::class.java)
            .hasMessage("Password Not Match")
    }
}
