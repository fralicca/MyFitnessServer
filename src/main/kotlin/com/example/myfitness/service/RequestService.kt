package com.example.myfitness.service

import com.example.myfitness.model.Richiesta
import java.util.*

interface RequestService {

    fun findAllRequests(): List<Richiesta>?

    fun insertRequest(request: Richiesta): Richiesta

    fun findRequestById(requestId: Int): Optional<Richiesta>

    fun deleteRequestId(requestId: Int)

    fun deleteAllRequests()

    fun getTrainerRequests(usernameId: String): List<Richiesta>?

}

