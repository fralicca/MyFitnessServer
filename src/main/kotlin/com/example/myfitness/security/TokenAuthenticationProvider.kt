package com.example.myfitness.security

import com.example.myfitness.service.UserAuthenticationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.authentication.BadCredentialsException
import org.apache.tomcat.jni.User.username
import android.R.attr.password
import org.slf4j.LoggerFactory
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Component
import java.util.*


@Component
class TokenAuthenticationProvider: AbstractUserDetailsAuthenticationProvider() {

    @Autowired
    lateinit var userAuthenticationService: UserAuthenticationService;

    private val logger = LoggerFactory.getLogger(TokenAuthenticationProvider::class.java)


    override fun retrieveUser(username: String?, authentication: UsernamePasswordAuthenticationToken?): UserDetails {
        var token = authentication!!.credentials


        return Optional
                .ofNullable(token)
                .flatMap { t ->
                    Optional.of(
                            userAuthenticationService.authenticateByToken(t.toString()))
                            .map { u ->
                                User.builder()
                                        .username(u.usernameId)
                                        .password(u.password)
                                        .roles("user")
                                        .build()
                            }
                }
                .orElseThrow { BadCredentialsException("Invalid authentication token=$token") }
    }

    @Throws(AuthenticationException::class)
    override fun additionalAuthenticationChecks(userDetails: UserDetails, usernamePasswordAuthenticationToken: UsernamePasswordAuthenticationToken) {


    }

}


