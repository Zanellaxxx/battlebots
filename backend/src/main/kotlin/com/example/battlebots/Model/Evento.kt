package com.example.battlebots.Model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Evento(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    val nome: String,
    val local: String,
    val dataHora: LocalDateTime,

    var abertoParaInscricao: Boolean = true,

    // Relacionamento N:N com Robo (Robôs inscritos no evento)
    @ManyToMany
    @JoinTable(
        name = "evento_robo",
        joinColumns = [JoinColumn(name = "evento_id")],
        inverseJoinColumns = [JoinColumn(name = "robo_id")]
    )
    val robosInscritos: MutableSet<Robo> = mutableSetOf(),

    @OneToMany(mappedBy = "evento", cascade = [CascadeType.ALL])
    val lutas: MutableList<Luta> = mutableListOf()
)