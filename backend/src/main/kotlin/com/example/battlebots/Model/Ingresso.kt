package com.example.battlebots.Model

import com.example.battlebots.Enums.TipoIngressoEnum
import jakarta.persistence.*

@Entity
class Ingresso(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    val evento: Evento,

    @Enumerated(EnumType.STRING)
    val tipo: TipoIngressoEnum,

    val preco: Double,

    var quantidadeDisponivel: Int
)