package com.example.myfitness.controller

import com.example.myfitness.model.Scheda
import com.example.myfitness.service.CardService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/** */
@RestController
@RequestMapping("/api/card")
class CardController {

    @Autowired
    lateinit var cardService: CardService
    private val logger = LoggerFactory.getLogger(CardController::class.java)


    /** Viene gestita la chiamata get che mostra tutti i membri*/
    @GetMapping("/all")
    fun getAllCards(): List<Scheda> = cardService.findAllCards()

    @PostMapping("/insert")
    fun createCard(@Valid @RequestBody card: Scheda): ResponseEntity<String> {
        cardService.insertCard(card)
        return ResponseEntity.ok("Inserita la scheda ${card.schedaId}")
    }

    /** Viene gestita la chiamata get che permette di cercare la presenza di un membro all'interno del db*/
    @GetMapping("/exists/{schedaId}/")
    fun getMemberById(@PathVariable(value = "schedaId") schedaId: Int): ResponseEntity<Scheda> {
        return with(cardService) {
            findCardById(schedaId).map { card ->
                ResponseEntity.ok(card)
            }.orElse(ResponseEntity.notFound().build())
        }
    }

    /** NON SO SE SERVE PERCHé IN CASO DI ID IDENTITO SOVRASCRIVE CON I NUOVI DATI **/
    @PutMapping("/update/{schedaId}/")
    fun updateCardById(@PathVariable(value = "schedaId") schedaId: Int, @Valid @RequestBody newCard: Scheda): ResponseEntity<Scheda> {
        return cardService.findCardById(schedaId).map { existingScheda ->
            val updateScheda: Scheda = existingScheda
                    .copy(num_giorni =  newCard.num_giorni, data = newCard.data, tipo = newCard.tipo, commento = newCard.commento,
                            autore = newCard.autore, possessore = newCard.possessore, esercizi = newCard.esercizi, is_current = newCard.is_current)
            ResponseEntity.ok().body(cardService.insertCard(updateScheda))
        }.orElse(ResponseEntity.notFound().build())
    }

    @DeleteMapping("/delete/{cardId}/")
    fun deleteCardById(@PathVariable(value = "cardId") cardId: Int) = cardService.deleteCard(cardId)

    @DeleteMapping("/delete/all")
    fun deleteAllCards(): ResponseEntity<String> {
        cardService.deleteAllCards()
        return ResponseEntity.ok("Cancellate tutte le schede nel DB OK!")
    }

    @DeleteMapping("/deleteAllUserCards/{usernameId}/")
    fun deleteAllUserCards(@PathVariable(value = "usernameId") usernameId: String): ResponseEntity<String> {
        cardService.deleteAllUserCards(usernameId)
        return ResponseEntity.ok("Cancellate tutte le schede dell'utente $usernameId")
    }

    @PutMapping("/setAsCurrentCard/{usernameId}/{cardId}/")
    fun setAsCurrentCard(@PathVariable(value = "usernameId") usernameId: String, @PathVariable(value = "cardId") cardId: Int): ResponseEntity<String> {
        cardService.setAsCurrentCard(usernameId, cardId)
        return ResponseEntity.ok("Scheda $cardId settata a $usernameId")
    }

    @DeleteMapping("/removeCurrentCard/{usernameId}/")
    fun removeCurrentCard(@PathVariable(value = "usernameId") usernameId: String): ResponseEntity<String> {
        cardService.removeCurrentCard(usernameId)
        return ResponseEntity.ok("Cancellata la scheda corrente dell'utente $usernameId")
    }

    @GetMapping("/getUserCards/{usernameId}/")
    fun getUserCards(@PathVariable(value = "usernameId") usernameId: String) =
            ResponseEntity.ok().body(cardService.getUserCards(usernameId))

    @GetMapping("/getCurrentCard/{usernameId}/")
    fun getCurrentCard(@PathVariable(value = "usernameId") usernameId: String) =
            ResponseEntity.ok().body(cardService.getCurrentCard(usernameId))

    @GetMapping("/getCompletedRequest/{usernameId}/")
    fun getCompletedRequest(@PathVariable(value = "usernameId") usernameId: String) =
            ResponseEntity.ok().body(cardService.getCompletedRequest(usernameId))

}