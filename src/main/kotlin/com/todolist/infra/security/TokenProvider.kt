package com.todolist.infra.security

import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import javax.crypto.spec.SecretKeySpec
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Component
import com.todolist.domain.user.entity.User
import java.sql.Timestamp
import java.time.LocalDateTime

@PropertySource("classpath:jwt.yml")
@Component
class TokenProvider(
    @Value("\${secret-key}")
    private val secretKey: String,
    @Value("\${access-expiration-hours}")
    private val accessExpirationHours: Long,
    @Value("\${issuer}")
    private val issuer: String
) {
    fun createToken(user: User): String {
        val accessClaim: Claims = Jwts.claims().apply {
            put("type", "access")
            put("email", user.email)
        }
        val accessToken: String = Jwts.builder()
            .signWith(SecretKeySpec(secretKey.toByteArray(), SignatureAlgorithm.HS256.jcaName))
            .setClaims(accessClaim)
            .setIssuer(issuer)
            .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
            .setExpiration(Date.from(Instant.now().plus(accessExpirationHours, ChronoUnit.HOURS)))
            .compact()

        return accessToken
    }
}