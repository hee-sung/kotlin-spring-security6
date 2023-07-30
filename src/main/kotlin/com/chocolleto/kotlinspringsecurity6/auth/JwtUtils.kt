package com.chocolleto.kotlinspringsecurity6.auth

import com.chocolleto.kotlinspringsecurity6.domain.entity.Account
import com.chocolleto.kotlinspringsecurity6.model.response.TokenResponse
import com.chocolleto.kotlinspringsecurity6.service.AccountDetailService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.text.SimpleDateFormat
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtils(
    private val accountDetailService: AccountDetailService
) {
    val accessTokenExpiredMs: Long = 1000L * 60 * 10
    val refreshTokenExpiredMs: Long = 1000L * 60 * 20
    val jwtSecret: String = "chocolletosecretchocolletosecretchocolletosecretchocolletosecret"
    val secretKey: SecretKey = Keys.hmacShaKeyFor(jwtSecret.toByteArray(StandardCharsets.UTF_8))
    val signatureAlg: SignatureAlgorithm = SignatureAlgorithm.HS256

    fun createToken(account: Account): TokenResponse {
        val claims: Claims = Jwts.claims()
        claims["id"] = account.id.toString()
        claims["email"] = account.email

        val now = Date()
        val accessTokenExpired = Date(now.time + accessTokenExpiredMs)
        val refreshTokenExpired = Date(now.time + refreshTokenExpiredMs)

        val accessToken: String = Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(now)
            .setExpiration(accessTokenExpired)
            .signWith(secretKey, signatureAlg)
            .compact()

        val refreshToken: String = Jwts.builder()
            .setClaims(claims)
            .setExpiration(refreshTokenExpired)
            .signWith(secretKey, signatureAlg)
            .compact()

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return TokenResponse(accessToken, refreshToken, dateFormat.format(accessTokenExpired), dateFormat.format(refreshTokenExpired), "bearer")
    }

    fun validation(token: String): Boolean {
        val claims: Claims = getAllClaims(token)
        val exp: Date = claims.expiration
        return exp.after(Date())
    }

    fun parseEmail(token: String): String {
        val claims: Claims = getAllClaims(token)
        return claims["email"] as String
    }

    fun parseUserId(token: String): Long {
        val claims: Claims = getAllClaims(token)
        val id = claims["id"] as String
        return id.toLong()
    }

    fun getAuthentication(username: String): Authentication {
        val userDetails: UserDetails = accountDetailService.loadUserByUsername(username)
        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    private fun getAllClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }
}