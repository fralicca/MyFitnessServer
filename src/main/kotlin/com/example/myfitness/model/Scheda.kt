package com.example.myfitness.model

import javax.persistence.*


@Entity
@Table(name="SCHEDA")
data class Scheda(
        @Id
        var schedaId: Int,
        var num_giorni: Int = -1,
        var data: String = "",
        var tipo: String = "",
        var commento: String = "",
        @ManyToOne(fetch= FetchType.LAZY) // Utente (allenatore) può comporre diverse schede a diversi utenti quindi diverse schede(autore) che puntano a un singolo utente
        @JoinColumn(name="autore")
        var autore: Utente,
        @ManyToOne(fetch= FetchType.LAZY) // Utente può avere diverse schede
        @JoinColumn(name="possessore")
        var possessore: Utente,
        @Convert(converter = EserciziConverter::class)
        var esercizi: ArrayList<ArrayList<Esercizio>> = ArrayList(),
        var is_current: Boolean = false)
{}
