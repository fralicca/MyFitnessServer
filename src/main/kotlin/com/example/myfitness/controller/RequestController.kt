package com.example.myfitness.controller

import com.example.myfitness.model.Richiesta
import com.example.myfitness.service.RequestService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/request")
class RequestController {

    @Autowired
    lateinit var requestService: RequestService
    private val logger = LoggerFactory.getLogger(RequestController::class.java)


    /** Viene gestita la chiamata get che mostra tutti i membri*/
    @GetMapping("/all")
    fun getAllRequests(): List<Richiesta>? = requestService.findAllRequests()

    @PostMapping("/insert")
    fun createRequest(@Valid @RequestBody request: Richiesta): ResponseEntity<String> {
        requestService.insertRequest(request)
        return ResponseEntity.ok("Inserita l    a richiesta ${request.richiestaId}")
    }

    /** Viene gestita la chiamata get che permette di cercare la presenza di un membro all'interno del db*/
    @GetMapping("/exists/{requestId}/")
    fun getMemberById(@PathVariable(value = "requestId") requestId: Int): ResponseEntity<Richiesta> {
        return with(requestService) {
            findRequestById(requestId).map { request ->
                ResponseEntity.ok(request)
            }.orElse(ResponseEntity.notFound().build())
        }
    }

    /** NON SO SE SERVE PERCHé IN CASO DI ID IDENTITO SOVRASCRIVE CON I NUOVI DATI **/
    @PutMapping("/update/{requestId}/")
    fun updateRequestById(@PathVariable(value = "requestId") requestId: Int, @Valid @RequestBody newRequest: Richiesta): ResponseEntity<Richiesta> {
        return requestService.findRequestById(requestId).map { existingRequest ->
            val updateRequest: Richiesta = existingRequest
                    .copy(utente =  newRequest.utente, allenatore = newRequest.allenatore, data = newRequest.data, numGiorni = newRequest.numGiorni,
                            tipologia = newRequest.tipologia, commento = newRequest.commento)
            ResponseEntity.ok().body(requestService.insertRequest(updateRequest))
        }.orElse(ResponseEntity.notFound().build())
    }


    @DeleteMapping("/delete/{requestId}/")
    fun deleteRequestById(@PathVariable(value = "requestId") requestId: Int) = requestService.deleteRequestId(requestId)

    @DeleteMapping("/delete/all")
    fun deleteAllRequests(): ResponseEntity<String> {
        requestService.deleteAllRequests()
        return ResponseEntity.ok("Cancellate tutte le richieste nel DB OK!")
    }

    @GetMapping("/trainerRequests/{usernameId}/")
    fun getTrainerRequests(@PathVariable(value = "usernameId") usernameId: String) = requestService.getTrainerRequests(usernameId)
}