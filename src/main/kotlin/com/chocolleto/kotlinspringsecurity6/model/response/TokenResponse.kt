package com.chocolleto.kotlinspringsecurity6.model.response

import com.chocolleto.kotlinspringsecurity6.domain.entity.Account

data class TokenResponse(val accessToken: Account?, val tokenType: String)
