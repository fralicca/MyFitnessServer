package com.example.myfitness.model

import java.util.*
import javax.persistence.*

@Entity
data class ConfirmationToken(
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "token_id") //Come la mappa sul db
        var tokenid: Long? = 0,
        @Column(name = "confirmation_token")
        var confirmationToken: String,
        @Temporal(TemporalType.TIMESTAMP)
        var createdDate: Date,
        @OneToOne(targetEntity = Utente::class, fetch = FetchType.EAGER)
        @JoinColumn(nullable = false, name = "usernameId")
        var user: Utente) {

    override fun toString(): String {
        return "ConfirmationToken(tokenid=$tokenid, confirmationToken='$confirmationToken', createdDate=$createdDate, user=$user)"
    }

}


