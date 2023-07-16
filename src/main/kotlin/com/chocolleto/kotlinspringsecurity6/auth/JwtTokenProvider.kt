package com.chocolleto.kotlinspringsecurity6.auth

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import java.time.*
import java.util.Date

class JwtTokenProvider {
    private val secretKey: String = "chocolleto"
    private val validityInMilliseconds: Long = 6_000_000

    fun createToken(subject: String): String {
        val claims: Claims = Jwts.claims().setSubject(subject)

        val now = Date()
        val expiration = Date(now.time + validityInMilliseconds)

        return Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact()
    }

    fun getSubject(token: String): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    fun validateToken(token: String): Boolean {
        try {
            val claims: Jws<Claims> = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            return !claims.body.expiration.before(Date())
        } catch (e: Exception) {
            return false
        }
    }
}