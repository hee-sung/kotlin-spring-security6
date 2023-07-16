package com.chocolleto.kotlinspringsecurity6.service

import com.chocolleto.kotlinspringsecurity6.auth.JwtTokenProvider
import com.chocolleto.kotlinspringsecurity6.domain.entity.Account
import com.chocolleto.kotlinspringsecurity6.domain.repository.AccountRepository
import com.chocolleto.kotlinspringsecurity6.model.request.AccountRequest
import com.chocolleto.kotlinspringsecurity6.model.request.LoginRequest
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.math.log

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun create(accountRequest: AccountRequest): Account {
        val password = passwordEncoder.encode(accountRequest.password)
        val account = Account(email = accountRequest.email, password = password)

        return accountRepository.save(account)
    }

    fun createToken(loginRequest: LoginRequest): String {
        val account = accountRepository.findByEmail(loginRequest.email) ?: throw IllegalArgumentException()
        return jwtTokenProvider.createToken(loginRequest.email)
    }
}