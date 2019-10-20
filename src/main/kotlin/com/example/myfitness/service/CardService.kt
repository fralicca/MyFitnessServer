package com.example.myfitness.service

import com.example.myfitness.model.Scheda
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile;
import java.net.URL
import java.nio.file.Path
import java.util.*
import java.util.stream.Stream

interface CardService {

    fun findAllCards(): List<Scheda>

    fun insertCard(card: Scheda): Scheda

    fun findCardById(cardId: Int): Optional<Scheda>

    fun deleteCard(cardId: Int)

    fun deleteAllCards()

    fun deleteAllUserCards(usernameId: String)

    fun setAsCurrentCard(usernameId: String, cardId: Int)

    fun removeCurrentCard(usernameId: String)

    fun getUserCards(usernameId: String): List<Scheda>?

    fun getCurrentCard(usernameId: String): Scheda?

    fun getCompletedRequest(usernameId: String):  List<Scheda>?

}

