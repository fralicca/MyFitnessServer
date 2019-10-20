package com.example.myfitness.config

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.security.web.AuthenticationEntryPoint
import com.example.myfitness.security.TokenAuthenticationFilter
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import com.example.myfitness.security.TokenAuthenticationProvider
import com.example.myfitness.service.SuccessHandlerRequest
import org.jasypt.util.password.StrongPasswordEncryptor
import org.springframework.security.web.util.matcher.NegatedRequestMatcher
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.OrRequestMatcher


import org.springframework.http.HttpStatus.FORBIDDEN
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfiguration : WebSecurityConfigurerAdapter() {

    private val PUBLIC_URLS = OrRequestMatcher(
            AntPathRequestMatcher("/**/insert"),
            AntPathRequestMatcher("/**/confirm-account"),
            AntPathRequestMatcher("/**/login/**/**/"),
            AntPathRequestMatcher("/**/insert"),
            AntPathRequestMatcher("/**/save/image"),
            AntPathRequestMatcher("/**/download/image/**/"),
            AntPathRequestMatcher("/**/login/**/**")
    )

    private val PROTECTED_URLS = NegatedRequestMatcher(PUBLIC_URLS)

    @Autowired
    lateinit var authenticationProvider: TokenAuthenticationProvider

    @Autowired
    lateinit var  successRequest: SuccessHandlerRequest

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider)
    }

    override fun configure(web: WebSecurity) {
        web.ignoring().requestMatchers(PUBLIC_URLS)
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
                .sessionManagement().sessionCreationPolicy(STATELESS)
                .and()
                .exceptionHandling()
                .defaultAuthenticationEntryPointFor(forbiddenEntryPoint(), PROTECTED_URLS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(restAuthenticationFilter(), AnonymousAuthenticationFilter::class.java)
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .logout().disable()


    }

    @Bean
    @Throws(Exception::class)
    fun restAuthenticationFilter(): TokenAuthenticationFilter {
        val filter = TokenAuthenticationFilter(PROTECTED_URLS)
        filter.setAuthenticationManager(authenticationManager())
        filter.setAuthenticationSuccessHandler(successRequest)
        return filter
    }

    @Bean
    fun forbiddenEntryPoint(): AuthenticationEntryPoint {
        return HttpStatusEntryPoint(FORBIDDEN)
    }


    @Bean
    fun strongEncryptor(): StrongPasswordEncryptor {
        return StrongPasswordEncryptor()
    }

}
