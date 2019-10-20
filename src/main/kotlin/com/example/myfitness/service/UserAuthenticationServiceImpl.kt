package com.example.myfitness.service

import com.example.myfitness.model.Utente
import com.example.myfitness.repository.ConfirmationTokenRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.stereotype.Service
import java.util.*
import javax.naming.AuthenticationException
import java.util.Base64.getEncoder



@Service
class UserAuthenticationServiceImpl : UserAuthenticationService {


    @Autowired
    lateinit var confirmationToken: ConfirmationTokenRepository

    @Autowired
    lateinit var passwordEncoder: EncryptionService

    override fun setPassword(password: String, utente: Utente): Utente {
        utente.password = passwordEncoder.encryptString(password)

        return utente;
    }


    override fun login(usernamId: String, password: String): String {
        var token = confirmationToken.findByUsernameId(usernamId)

        if (token != null && passwordEncoder.checkPassword(password, token.user.password)) {
            return token.confirmationToken
        } else {
            throw BadCredentialsException("Credenziali non valide")
        }
    }

    override fun authenticateByToken(token: String): Utente {
        val utente: Utente? = confirmationToken.findByConfirmationToken(token)?.user

        if (utente != null)
            return utente
        else
            throw AuthenticationException("Token non trovato")
    }

}