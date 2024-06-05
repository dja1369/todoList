package com.todolist.domain.todo.module

import com.todolist.domain.todo.enums.Status
import com.todolist.domain.todo.vo.TodoVo
import com.todolist.domain.todo.vo.TodoVoDetail
import com.todolist.domain.todo.vo.TodoVoList

interface TodoService {
    /**
     * 할일 생성
     */
    fun createTodo(title: String, content: String?): TodoVoDetail

    /**
     * 할일 수정
     */
    fun updateTodo(id: String, content: String, newStatus: Status): TodoVoDetail

    /**
     * 할일 삭제
     */
    fun deleteTodo(id: String): Boolean

    /**
     * 가장 최근 할일 조회
     */
    fun getMostRecentTodo(): TodoVo

    /**
     * 할일 전체 조회
     */
    fun getAllTodo(): TodoVoList

    /**
     * 할일 상세 조회
     */
    fun getTodoDetail(id: String): TodoVoDetail
}