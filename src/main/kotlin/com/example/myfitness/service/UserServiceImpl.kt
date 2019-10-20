package com.example.myfitness.service

import com.example.myfitness.model.Utente
import com.example.myfitness.repository.UserRepository
import org.jasypt.util.password.StrongPasswordEncryptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Service
import javax.transaction.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder





@Service
@Transactional
class UserServiceImpl: UserService {


    @Autowired
    lateinit var userRepository: UserRepository

    override fun findAllUsers() = userRepository.findAll()

    override fun insertUser(user: Utente): Utente {
        return userRepository.save(user)
    }

    override fun findUserById(usernameId: String) = userRepository.findById(usernameId)

    override fun deleteUser(user: Utente) = userRepository.delete(user)

    override fun deleteAllUsers() = userRepository.deleteAll()

    override fun findByEmailIgnoreCase(email: String) = userRepository.findByEmailIgnoreCase(email)

}