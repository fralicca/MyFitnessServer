package com.example.myfitness.service

import com.example.myfitness.model.Utente
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*

interface UserService {

    fun findAllUsers(): List<Utente>

    fun insertUser(user: Utente): Utente

    fun findUserById(usernameId: String): Optional<Utente>

    fun deleteUser(user: Utente)

    fun deleteAllUsers()

    fun findByEmailIgnoreCase(email: String): Utente

}