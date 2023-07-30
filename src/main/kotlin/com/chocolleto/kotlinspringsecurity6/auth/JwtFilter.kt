package com.chocolleto.kotlinspringsecurity6.auth

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtFilter(
    private val jwtUtils: JwtUtils
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = parseToken(request) ?: return filterChain.doFilter(request, response)

        if (jwtUtils.validation(token)) {
            val username = jwtUtils.parseEmail(token)
            val authentication: Authentication = jwtUtils.getAuthentication(username)

            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun parseToken(request: HttpServletRequest): String? {
        val header: String? = request.getHeader("Authorization") ?: return null
        return header?.substring("Bearer ".length) ?: return null
    }
}