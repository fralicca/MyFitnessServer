package com.example.myfitness.service

import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class SuccessHandlerRequest : AuthenticationSuccessHandler {
    override fun onAuthenticationSuccess(p0: HttpServletRequest?, p1: HttpServletResponse?, p2: Authentication?) {

    }

}