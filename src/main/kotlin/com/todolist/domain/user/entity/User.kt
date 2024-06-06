package com.todolist.domain.user.entity

import com.todolist.domain.common.BaseEntity
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "t_user")
class User(
    @Column(name = "nick_name", unique = true, nullable = false, length = 100)
    val nickName: String,

    @Column(name = "email", nullable = false)
    val email: String,

    @Column(name = "password", nullable = false)
    val password: String,

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID = UUID.randomUUID()

): BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (email != other.email) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = email.hashCode()
        result = 31 * result + id.hashCode()
        return result
    }

    override fun toString(): String {
        return "User(id=$id, nickName='$nickName', email='$email')"
    }

}