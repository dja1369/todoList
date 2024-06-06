package com.todolist.unittest.todo

import com.todolist.aplication.commonRepository.UserRepository
import com.todolist.aplication.todo.repository.TodoRepository
import com.todolist.domain.todo.Todo
import com.todolist.domain.todo.enums.Status
import com.todolist.domain.todo.module.TodoService
import com.todolist.domain.todo.vo.TodoVo
import com.todolist.domain.todo.vo.TodoVoDetail
import com.todolist.domain.todo.vo.TodoVoList
import com.todolist.domain.user.entity.User
import com.todolist.utils.ConverterUtil
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDateTime
import java.util.*

@SpringBootTest
class TodoServiceTest {
    @MockBean
    private lateinit var todoRepository: TodoRepository

    @MockBean
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var todoService: TodoService

    private lateinit var userId: UUID

    private lateinit var mockConverterUtil: ConverterUtil


    @BeforeEach
    fun setUp() {
        userId = UUID.randomUUID()
        val mockAuthentication = mock(Authentication::class.java)
        `when`(mockAuthentication.principal).thenReturn(userId)

        val mockSecurityContext = mock(SecurityContext::class.java)
        `when`(mockSecurityContext.authentication).thenReturn(mockAuthentication)

        SecurityContextHolder.setContext(mockSecurityContext)

        val authentication = SecurityContextHolder.getContext().authentication
        authentication.principal

        mockConverterUtil = mock(ConverterUtil::class.java)
    }

    @Test
    fun `할 일 생성 성공 테스트`() {
        // given
        val title = "Test Todo"
        val content = "This is a test todo"
        val user = User("test@test.com", "test", "password")
        val todo = Todo(title = title, content = content, user = user)

        `when`(userRepository.findById(userId)).thenReturn(Optional.of(user))
        `when`(todoRepository.save(todo)).thenReturn(todo)

        // when
        val result = todoService.createTodo(title, content)

        // then
        assertThat(result.title).isEqualTo(title)
        assertThat(result.content).isEqualTo(content)
    }

    @Test
    fun `할일 삭제 성공 테스트`() {
        // given
        val uuidId = UUID.randomUUID()
        val todo = Todo(
            title = "Test Todo",
            content = "This is a test todo",
            user = User(email = "test@test.com", nickName = "test", password = "password")
        )
        `when`(todoRepository.findByUuidIdAndDeletedAtIsNull(uuidId)).thenReturn(todo)

        // when
        val result = todoService.deleteTodo(uuidId.toString())

        // then
        assertThat(result).isTrue()
        assertThat(todo.deletedAt).isNotNull()
    }

    @Test
    fun `할일 삭제 실패 테스트`() {
        // given
        val uuidId = UUID.randomUUID()
        `when`(todoRepository.findByUuidIdAndDeletedAtIsNull(uuidId)).thenReturn(null)

        // when && then
        assertThatThrownBy {
            todoService.deleteTodo(uuidId.toString())
        }.isInstanceOf(Exception::class.java)
            .hasMessage("Todo Not Found")
    }

    @Test
    fun `할일 수정 성공 이벤트`() {
        // given
        val uuidId = UUID.randomUUID()
        val mockUser = mock(User::class.java)
        val mockTodo = Todo(
            title = "Test Todo",
            content = "This is a test todo",
            user = mockUser,
            uuidId = uuidId
        )

        `when`(todoRepository.findByUuidIdAndDeletedAtIsNull(uuidId)).thenReturn(mockTodo)
        `when`(todoRepository.save(mockTodo)).thenReturn(mockTodo)

        // when
        val result = todoService.updateTodo(
            uuidId.toString(),
            "This is a updated test todo",
            Status.IN_PROGRESS
        )

        // then
        assertThat(result).isNotNull()
        assertThat(result).isInstanceOf(TodoVoDetail::class.java)
        assertThat(result.status).isEqualTo(Status.IN_PROGRESS)

        // when
        val result2 = todoService.updateTodo(
            uuidId.toString(),
            "This is a updated test todo",
            Status.PENDING
        )

        // then
        assertThat(result2).isNotNull()
        assertThat(result2).isInstanceOf(TodoVoDetail::class.java)
        assertThat(result2.status).isEqualTo(Status.PENDING)

        // when
        val result3 = todoService.updateTodo(
            uuidId.toString(),
            "This is a updated test todo",
            Status.DONE
        )

        // then
        assertThat(result3).isNotNull()
        assertThat(result3).isInstanceOf(TodoVoDetail::class.java)
        assertThat(result3.status).isEqualTo(Status.DONE)
    }

    @Test
    fun `할일 수정 조건 테스트`() {
        // given
        val uuidId = UUID.randomUUID()
        val mockUser = mock(User::class.java)
        val mockTodo = Todo(
            title = "Test Todo",
            content = "This is a test todo",
            user = mockUser,
            uuidId = uuidId
        )

        `when`(todoRepository.findByUuidIdAndDeletedAtIsNull(uuidId)).thenReturn(mockTodo)
        // when && then
        assertThatThrownBy {
            todoService.updateTodo(
                uuidId.toString(),
                "This is a updated test todo",
                Status.PENDING
            )
        }.isInstanceOf(Exception::class.java)
            .hasMessage("PENDING은 오직 IN_PROGRESS 상태에서만 변경 가능합니다.")

        // given
        mockTodo.status = Status.IN_PROGRESS

        // when
        val result = todoService.updateTodo(
            uuidId.toString(),
            "This is a updated test todo",
            Status.PENDING
        )

        // then
        assertThat(result).isNotNull()
        assertThat(result).isInstanceOf(TodoVoDetail::class.java)
        assertThat(result.status).isEqualTo(Status.PENDING)

        // given
        mockTodo.status = Status.DONE

        // when && then
        assertThatThrownBy {
            todoService.updateTodo(
                uuidId.toString(),
                "This is a updated test todo",
                Status.PENDING
            )
        }.isInstanceOf(Exception::class.java)
            .hasMessage("PENDING은 오직 IN_PROGRESS 상태에서만 변경 가능합니다.")
    }
    @Test
    fun `할일 전부 가져오기 테스트`() {
        // given
        val mockUser = mock(User::class.java)
        val mockTodo = Todo(
            title = "Test Todo",
            content = "This is a test todo",
            user = mockUser
        )

        `when`(mockConverterUtil.jwtIdToUUID()).thenReturn(userId)
        `when`(todoRepository.findAllByUserIdAndDeletedAtIsNull(mockConverterUtil.jwtIdToUUID())).thenReturn(
            listOf(
                mockTodo,
                mockTodo,
                mockTodo
            )
        )
        // when
        val result = todoService.getAllTodo()

        // then
        assertThat(result.todoList.size).isEqualTo(3)
        assertThat(result).isInstanceOf(TodoVoList::class.java)

    }

    @Test
    fun `할일 전부 가져오기 실패 테스트`() {
        // given
        `when`(mockConverterUtil.jwtIdToUUID()).thenReturn(userId)
        `when`(todoRepository.findAllByUserIdAndDeletedAtIsNull(mockConverterUtil.jwtIdToUUID())).thenReturn(null)

        // when && then
        assertThatThrownBy {
            todoService.getAllTodo()
        }.isInstanceOf(Exception::class.java)
            .hasMessage("Todo Not Found")
    }

    @Test
    fun `가장 최근 할일 가져오기 테스트`() {
        // given
        val mockUser = mock(User::class.java)
        val mockTodoByUpdatedAt = Todo(
            title = "Test Todo",
            content = "This is a test todo",
            user = mockUser,
        )
        mockTodoByUpdatedAt.updatedAt = LocalDateTime.now()
        val mockTodoByCreatedAt = Todo(
            title = "Test Todo",
            content = "This is a test todo",
            user = mockUser,
        )
        mockTodoByCreatedAt.createdAt = LocalDateTime.now().minusDays(1)

        `when`(mockConverterUtil.jwtIdToUUID()).thenReturn(userId)
        `when`(todoRepository.findByUserIdAndDeletedAtIsNullOrderByUpdatedAtDesc(mockConverterUtil.jwtIdToUUID())).thenReturn(mockTodoByUpdatedAt)
        `when`(todoRepository.findByUserIdAndDeletedAtIsNullOrderByCreatedAtDesc(mockConverterUtil.jwtIdToUUID())).thenReturn(mockTodoByCreatedAt)

        // when
        val result = todoService.getMostRecentTodo()

        // then
        assertThat(result).isNotNull()
        assertThat(result).isInstanceOf(TodoVo::class.java)
        assertThat(result).isEqualTo(mockTodoByUpdatedAt.toVo())

        // given
        mockTodoByUpdatedAt.updatedAt = LocalDateTime.now().minusDays(1)
        mockTodoByCreatedAt.createdAt = LocalDateTime.now()

        // when
        val result2 = todoService.getMostRecentTodo()

        // then
        assertThat(result2).isNotNull()
        assertThat(result2).isInstanceOf(TodoVo::class.java)
        assertThat(result2).isEqualTo(mockTodoByCreatedAt.toVo())
    }

    @Test
    fun `할일 디테일 가져오기 성공 테스트`() {
        // given
        val uuidId = UUID.randomUUID()
        val todo = Todo(
            title = "Test Todo",
            content = "This is a test todo",
            user = mock(User::class.java)
        )
        `when`(todoRepository.findByUuidIdAndDeletedAtIsNull(uuidId)).thenReturn(todo)

        // when
        val result = todoService.getTodoDetail(uuidId.toString())

        // then
        assertThat(result.title).isEqualTo(todo.title)
    }

    @Test
    fun `할일 디테일 가져오기 실패 테스트`() {
        // given
        val uuidId = UUID.randomUUID()
        `when`(todoRepository.findByUuidIdAndDeletedAtIsNull(uuidId)).thenReturn(null)

        // when && then
        assertThatThrownBy {
            todoService.getTodoDetail(uuidId.toString())
        }.isInstanceOf(Exception::class.java)
            .hasMessage("Todo Not Found")
    }
}