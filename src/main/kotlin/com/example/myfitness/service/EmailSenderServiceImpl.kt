package com.example.myfitness.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender

@Service
class EmailSenderServiceImpl: EmailSenderService{

    @Autowired
    lateinit var javaMailSender: JavaMailSender

    @Async
    override fun sendEmail(email: SimpleMailMessage) {
        javaMailSender.send(email);
    }
}
