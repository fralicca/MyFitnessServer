package com.example.myfitness.service

import com.example.myfitness.model.Utente
import org.springframework.security.authentication.BadCredentialsException
import javax.naming.AuthenticationException


interface UserAuthenticationService {

    @Throws(BadCredentialsException::class)
    fun login(usernamId: String, password: String): String

    @Throws(AuthenticationException::class)
    fun authenticateByToken(token: String): Utente

    fun setPassword(password: String, utente: Utente): Utente

}