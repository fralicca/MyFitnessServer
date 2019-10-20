package com.example.myfitness.model

import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name="UTENTE")
data class Utente(
        @Id
        var usernameId: String,
        var mail: String,
        var password: String,
        var flagAllenatore: Boolean = false,
        var nome: String,
        var cognome: String,
        var eta: Int? = null,
        var descrizione: String? = null,
        var imageURI: String? = null,
        var peso: Double? = null,
        var altezza: Int? = null,
        var genere: Char,
        var allenatore: String? = null,
        var idScheda: Int? = null, //???
        var isEnabled: Boolean = false)
{
    override fun toString(): String {
        return "Utente(usernameId='$usernameId', mail='$mail', password='$password', flagAllenatore=$flagAllenatore, nome='$nome', cognome='$cognome', eta=$eta, descrizione=$descrizione, imageURI=$imageURI, peso=$peso, altezza=$altezza, genere=$genere, allenatore=$allenatore, idScheda=$idScheda, isEnabled=$isEnabled)"
    }
}