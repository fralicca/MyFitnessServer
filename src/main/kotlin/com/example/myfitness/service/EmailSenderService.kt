package com.example.myfitness.service

import org.springframework.mail.SimpleMailMessage

interface EmailSenderService {

    fun sendEmail(email: SimpleMailMessage)

}