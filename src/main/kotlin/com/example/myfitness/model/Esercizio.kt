package com.example.myfitness.model

import javax.persistence.Entity

data class Esercizio(
        var nome: String = "",
        var serie: Int? = null,
        var ripetizioni: Int? = null,
        var recupero: Double? = null,
        var commento: String = ""){

    override fun toString(): String {
        return "Esercizio(nome='$nome', serie=$serie, ripetizioni=$ripetizioni, " +
                "recupero=$recupero, commento='$commento')"
    }

}