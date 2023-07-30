package com.chocolleto.kotlinspringsecurity6.service

import com.chocolleto.kotlinspringsecurity6.auth.JwtUtils
import com.chocolleto.kotlinspringsecurity6.domain.entity.Account
import com.chocolleto.kotlinspringsecurity6.domain.repository.AccountRepository
import com.chocolleto.kotlinspringsecurity6.model.request.AccountRequest
import com.chocolleto.kotlinspringsecurity6.model.request.LoginRequest
import com.chocolleto.kotlinspringsecurity6.model.request.RefreshRequest
import com.chocolleto.kotlinspringsecurity6.model.response.TokenResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.math.log

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils
) {

    fun create(accountRequest: AccountRequest): Account {
        val password = passwordEncoder.encode(accountRequest.password)
        val account = Account(email = accountRequest.email, password = password)

        return accountRepository.save(account)
    }

    fun login(accountRequest: AccountRequest): TokenResponse {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(accountRequest.email, accountRequest.password, null)
            )
        } catch (e: BadCredentialsException) {
            throw BadCredentialsException("로그인 실패")
        }

        val account = accountRepository.findByEmail(accountRequest.email)

        return jwtUtils.createToken(account!!)
    }

    fun reissueToken(refreshRequest: RefreshRequest): TokenResponse {
        val valid: Boolean = jwtUtils.validation(refreshRequest.refreshToken)
        if (valid) {
            val email: String = jwtUtils.parseEmail(refreshRequest.refreshToken)
            val account = accountRepository.findByEmail(email)
            return jwtUtils.createToken(account!!)
        }

        throw BadCredentialsException("토큰 재발급 실패")
    }
}