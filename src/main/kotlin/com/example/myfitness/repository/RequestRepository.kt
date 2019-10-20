package com.example.myfitness.repository

import com.example.myfitness.model.Richiesta
import com.example.myfitness.model.Scheda
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface RequestRepository: JpaRepository<Richiesta, Int> {

    @Query("SELECT * FROM Richiesta r WHERE r.allenatore = :username", nativeQuery = true)
    fun getTrainerRequests(@Param("username") username: String):  List<Richiesta>?

}

