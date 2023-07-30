package com.chocolleto.kotlinspringsecurity6.controller

import com.chocolleto.kotlinspringsecurity6.auth.JwtUtils
import com.chocolleto.kotlinspringsecurity6.model.request.AccountRequest
import com.chocolleto.kotlinspringsecurity6.model.request.RefreshRequest
import com.chocolleto.kotlinspringsecurity6.model.response.TokenResponse
import com.chocolleto.kotlinspringsecurity6.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/auth")
class AuthController(
    private val accountService: AccountService) {

    @PostMapping("/register")
    fun create(@RequestBody accountRequest: AccountRequest): ResponseEntity<Any> {
        val account = accountService.create(accountRequest)
        return ResponseEntity.ok().body(account)
    }

    @PostMapping("/login")
    fun login(@RequestBody accountRequest: AccountRequest): ResponseEntity<Any> {
        val token: TokenResponse = accountService.login(accountRequest)
        return ResponseEntity.ok().body(token)
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody refreshRequest: RefreshRequest): ResponseEntity<Any> {
        val token: TokenResponse = accountService.reissueToken(refreshRequest)
        return ResponseEntity.ok().body(token)
    }
}