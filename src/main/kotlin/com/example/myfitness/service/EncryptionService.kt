package com.example.myfitness.service

interface EncryptionService {

    fun encryptString(input: String): String

    fun checkPassword(plainPassword: String, encryptedPassword: String): Boolean

}