package com.example.myfitness.repository

import com.example.myfitness.model.ConfirmationToken
import com.example.myfitness.model.Utente
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param


interface ConfirmationTokenRepository : JpaRepository<ConfirmationToken, Long> {

    @Query("SELECT * FROM confirmation_token c WHERE c.confirmation_token = :confirmationToken LIMIT 1", nativeQuery = true)
    fun findByConfirmationToken(@Param("confirmationToken") confirmationToken: String): ConfirmationToken?

    @Query("SELECT * FROM confirmation_token c WHERE c.username_id like :usernameId LIMIT 1", nativeQuery = true)
    fun findByUsernameId(@Param("usernameId") usernameId: String): ConfirmationToken?

}

