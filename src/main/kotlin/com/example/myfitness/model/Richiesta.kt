package com.example.myfitness.model

import javax.persistence.*

@Entity
@Table(name ="RICHIESTA")
data class Richiesta (
    @Id
    var richiestaId: Int,
    @ManyToOne(fetch= FetchType.LAZY) // Utente (allenatore) può comporre diverse schede a diversi utenti quindi diverse schede(autore) che puntano a un singolo utente
    @JoinColumn(name="utente")
    var utente: Utente,
    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="allenatore")
    var allenatore: Utente,
    var data: String,
    var numGiorni: Int,
    var tipologia: String,
    var commento: String)
{}
