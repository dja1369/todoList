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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
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
        }
        val accessToken: String = Jwts.builder()
            .signWith(SecretKeySpec(secretKey.toByteArray(), SignatureAlgorithm.HS256.jcaName))
            .setClaims(accessClaim)
            .setIssuer(issuer)
            .setSubject(user.id.toString())
            .setIssuedAt(Timestamp.valueOf(LocalDateTime.now()))
            .setExpiration(Date.from(Instant.now().plus(accessExpirationHours, ChronoUnit.HOURS)))
            .compact()
        return accessToken
    }

    fun validateToken(token: String): Boolean {
        return try{
            val claims = Jwts.parserBuilder()
                .setSigningKey(SecretKeySpec(secretKey.toByteArray(), SignatureAlgorithm.HS256.jcaName))
                .build()
                .parseClaimsJws(token)
            claims.body.expiration.after(Date.from(Instant.now()))
        } catch(e: Exception) {
            return false
        }
    }

    fun authenticateToken(token: String): Authentication {
        val claims = Jwts.parserBuilder()
            .setSigningKey(SecretKeySpec(secretKey.toByteArray(), SignatureAlgorithm.HS256.jcaName))
            .build()
            .parseClaimsJws(token)
        val authorities = listOf(SimpleGrantedAuthority("ROLE_USER"))
        return UsernamePasswordAuthenticationToken(claims.body.subject, null, authorities)
    }
}