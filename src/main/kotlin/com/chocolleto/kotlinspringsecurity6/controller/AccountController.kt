package com.chocolleto.kotlinspringsecurity6.controller

import com.chocolleto.kotlinspringsecurity6.model.request.AccountRequest
import com.chocolleto.kotlinspringsecurity6.model.request.LoginRequest
import com.chocolleto.kotlinspringsecurity6.model.response.TokenResponse
import com.chocolleto.kotlinspringsecurity6.service.AccountService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class AccountController(private val accountService: AccountService) {

    @PostMapping("/register")
    fun create(@RequestBody accountRequest: AccountRequest): ResponseEntity<Any> {
        val account = accountService.create(accountRequest)
        return ResponseEntity.ok().body(account)
    }

    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): ResponseEntity<Any> {
        val token = accountService.createToken(loginRequest)
        return ResponseEntity.ok().body(TokenResponse(token, "bearer"))
    }
}