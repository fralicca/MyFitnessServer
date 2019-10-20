package com.example.myfitness.security

import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication
import org.springframework.security.web.util.matcher.RequestMatcher
import java.io.IOException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest



open class TokenAuthenticationFilter(var requiresAuth: RequestMatcher): AbstractAuthenticationProcessingFilter(requiresAuth) {

    private val AUTHORIZATION = "Authorization"
    private val BEARER = "Bearer"

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val token = Optional.ofNullable(request.getHeader(AUTHORIZATION))
                .map { v -> v.replace(BEARER, "").trim() }
                .orElseThrow { BadCredentialsException("Missing authentication token.") }

        val auth = UsernamePasswordAuthenticationToken(token, token)
        return authenticationManager.authenticate(auth)
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
            request: HttpServletRequest,
            response: HttpServletResponse,
            chain: FilterChain?,
            authResult: Authentication) {
        super.successfulAuthentication(request, response, chain, authResult)
        chain!!.doFilter(request, response)
    }
}
