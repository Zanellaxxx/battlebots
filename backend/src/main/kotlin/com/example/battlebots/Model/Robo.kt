package com.example.battlebots.Model

import jakarta.persistence.*

@Entity
class Robo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    val nome: String,

    val pesoKg: Double, // Para categorização (Item g)
    val potenciaHp: Int, // Para categorização
    val armamentoPrincipal: String, // Ex: Flippers, Spinner, Axe, Wedge
    val ehArmaProibida: Boolean, // Ex: True se usar Fogo/Líquidos

    @ManyToOne // Relacionamento com o Competidor (Dono do robô)
    @JoinColumn(name = "competidor_id", nullable = false)
    val competidor: Competidor,

    // Categoria determinada automaticamente (Item g)
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    var categoria: Categoria? = null,

    // Relação N:N com Evento
    @ManyToMany(mappedBy = "robosInscritos")
    val eventosInscritos: MutableSet<Evento> = mutableSetOf()
)