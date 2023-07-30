package com.chocolleto.kotlinspringsecurity6.service

import UserNotFoundException
import com.chocolleto.kotlinspringsecurity6.auth.AccountDetails
import com.chocolleto.kotlinspringsecurity6.domain.repository.AccountRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class AccountDetailService(
    private val accountRepository: AccountRepository
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user = accountRepository.findByEmail(username) ?: throw UserNotFoundException()
        return AccountDetails(user)
    }
}