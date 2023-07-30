package com.chocolleto.kotlinspringsecurity6.model.response

import com.chocolleto.kotlinspringsecurity6.domain.entity.Account

data class TokenResponse(val accessToken: String, val refreshToken: String, val accessTokenExpired: String, val refreshTokenExpired: String, val tokenType: String)
