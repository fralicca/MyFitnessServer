package com.example.myfitness

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder


@SpringBootApplication
//@EntityScan(basePackages = arrayOf("com.example.myfitness.model"))
class MyfitnessApplication


fun main(args: Array<String>) {
    runApplication<MyfitnessApplication>(*args)
}

