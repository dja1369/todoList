package com.todolist.domain.todo

import com.todolist.domain.common.BaseEntity
import com.todolist.domain.todo.enums.Status
import com.todolist.domain.user.entity.User
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "t_todo",
       indexes = [Index(name = "idx_todo_id" , columnList = "id", unique = true)]
)
class Todo(

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    val user: User,

    @Column(name = "title", nullable = false, length = 200)
    val title: String,

    @Column(name = "content", nullable = false)
    var content: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    var status: Status = Status.TODO,

    @Column(name = "id", unique = true, nullable = false)
    val id: UUID = UUID.randomUUID(),

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var seq: Long = 0,

    ): BaseEntity(){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Todo) return false

        if (id != other.id) return false
        if (seq != other.seq) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + seq.hashCode()
        return result
    }

    override fun toString(): String {
        return "Todo(" +
                "seq=$seq, " +
                "id=$id, " +
                "status=$status, " +
                "content='$content', " +
                "title='$title'" +
                ")"
    }
}