package com.todolist.domain.todo

import com.todolist.domain.common.BaseEntity
import com.todolist.domain.todo.enums.Status
import com.todolist.domain.todo.vo.TodoVo
import com.todolist.domain.todo.vo.TodoVoDetail
import com.todolist.domain.user.entity.User
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "t_todo",
       indexes = [Index(name = "idx_todo_uuid_id" , columnList = "uuid_id", unique = true)]
)
class Todo(

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "title", nullable = false, length = 200)
    val title: String,

    @Column(name = "content", nullable = false)
    var content: String? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: Status = Status.TODO,

    @Column(name = "uuid_id", unique = true, nullable = false)
    val uuidId: UUID = UUID.randomUUID(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var seq: Long = 0,

    ): BaseEntity(){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Todo) return false

        if (uuidId != other.uuidId) return false
        if (seq != other.seq) return false

        return true
    }

    override fun hashCode(): Int {
        var result = uuidId.hashCode()
        result = 31 * result + seq.hashCode()
        return result
    }

    override fun toString(): String {
        return "Todo(" +
                "seq=$seq, " +
                "id=$uuidId, " +
                "status=$status, " +
                "content='$content', " +
                "title='$title'" +
                ")"
    }
    fun toVo() = TodoVo(
        id = uuidId.toString(),
        title = title,
        status = status
    )

    fun toVoDetail() = TodoVoDetail(
        id = uuidId.toString(),
        title = title,
        content = content,
        status = status
    )
}