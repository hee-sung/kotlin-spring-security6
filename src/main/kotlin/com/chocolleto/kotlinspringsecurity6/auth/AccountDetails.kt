package com.chocolleto.kotlinspringsecurity6.auth

import com.chocolleto.kotlinspringsecurity6.domain.entity.Account
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.userdetails.UserDetails

class AccountDetails(val account: Account): UserDetails {
    private val enabled: Boolean = true

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = AuthorityUtils.createAuthorityList()
    override fun getPassword(): String = account.password
    override fun getUsername(): String = account.email
    override fun isAccountNonExpired(): Boolean = enabled
    override fun isAccountNonLocked(): Boolean = enabled
    override fun isCredentialsNonExpired(): Boolean = enabled
    override fun isEnabled(): Boolean = enabled
}