package com.example.myfitness.service

import com.example.myfitness.model.Scheda
import com.example.myfitness.repository.CardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.transaction.Transactional


@Service
@Transactional
class CardServiceImpl: CardService {

    @Autowired
    lateinit var cardRepository: CardRepository


    override fun findAllCards() = cardRepository.findAll()

    override fun insertCard(card: Scheda) = cardRepository.save(card)

    override fun findCardById(cardId: Int) = cardRepository.findById(cardId)

    override fun deleteCard(cardId: Int) = cardRepository.deleteById(cardId)

    override fun deleteAllCards() = cardRepository.deleteAll()

    override fun deleteAllUserCards(usernameId: String) = cardRepository.deleteAllUserSchede(usernameId)

    override fun setAsCurrentCard(usernameId: String, cardId: Int) = cardRepository.setAsCurrentCard(usernameId, cardId)

    override fun removeCurrentCard(usernameId: String) = cardRepository.removeCurrentCard(usernameId)

    override fun getUserCards(usernameId: String) = cardRepository.getUserCards(usernameId)

    override fun getCurrentCard(usernameId: String) = cardRepository.getCurrentCard(usernameId)

    override fun getCompletedRequest(usernameId: String) = cardRepository.getCompletedRequest(usernameId)

}