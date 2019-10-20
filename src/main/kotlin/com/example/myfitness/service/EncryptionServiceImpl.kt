package com.example.myfitness.service

import org.jasypt.util.password.StrongPasswordEncryptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class EncryptionServiceImpl : EncryptionService {

    @Autowired
    lateinit var strongEncryptor: StrongPasswordEncryptor;


    override fun encryptString(input: String): String {
        return strongEncryptor.encryptPassword(input);
    }

    override fun checkPassword(plainPassword: String, encryptedPassword: String): Boolean {
        return strongEncryptor.checkPassword(plainPassword, encryptedPassword);
    }

}


