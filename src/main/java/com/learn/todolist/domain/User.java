package com.learn.todolist.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Table(name = "t_user")
@NoArgsConstructor
@ToString
@Getter
@SQLRestriction("deleted_at is NULL")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Setter
    @Column(name = "nick_name", unique = true, nullable = false)
    private String nickName;

    @Setter
    @Column(name = "password", nullable = false)
    private String password;

    @Builder
    public User(String email, String nickName, String password) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
    }
}
