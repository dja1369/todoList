package com.todolist.utils

import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

object ConverterUtil {
    /**
     * 매 요청마다 filter 에서 JWT 를 검증하고 새로 등록함
     * context 데이터는 요청시 마다 항상 복사되어 thread local에 저장되어 있음
     * 동시에 요청하더라도 각 쓰레드는 자신의 context 데이터를 사용함
     * -> thread safe
     * Convert JWT ID to UUID
     * @return UUID
     */
    fun jwtIdToUUID(): UUID {
        return UUID.fromString(SecurityContextHolder.getContext().authentication.principal.toString())
    }
}