package com.chocolleto.kotlinspringsecurity6.domain.repository

import com.chocolleto.kotlinspringsecurity6.domain.entity.Account
import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository: JpaRepository<Account, Long> {
    fun findByEmail(email: String): Account?
}