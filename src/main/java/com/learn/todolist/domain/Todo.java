package com.learn.todolist.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;

import java.util.UUID;

@Entity
@Table(name = "t_todo", indexes = {
        @Index(name = "idx_todo_id", columnList = "id")
})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLRestriction("deleted_at is NULL")
public class Todo extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;

    @Setter
    @Column(name = "content")
    private String content;

    @Setter
    @Column(name = "title", nullable = false)
    private String title;

    @JoinColumn(name = "user_id", nullable = false)
    private UUID userId;

    @Builder
    public Todo(String title, String content, UUID user){
        this.title = title;
        this.content = content;
        this.userId = user;
        this.status = Status.TODO;
    }
}

