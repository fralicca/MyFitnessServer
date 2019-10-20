package com.example.myfitness.security

import com.example.myfitness.controller.UserController
import org.slf4j.LoggerFactory
import org.springframework.security.web.RedirectStrategy
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest


class NoRedirectStrategy : RedirectStrategy {

    private val logger = LoggerFactory.getLogger(NoRedirectStrategy::class.java)

    override fun sendRedirect(request: HttpServletRequest, response: HttpServletResponse, url: String) {
        logger.info("Sono dentro a sendRedirect " + request.contextPath)
        logger.info("Sono dentro a sendRedirect " + request.requestURL)
        response.sendRedirect(request.requestURI)



    }
}