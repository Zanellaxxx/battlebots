package com.example.battlebots.Model

import com.example.battlebots.Enums.StatusLutaEnum
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Luta(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    val evento: Evento,

    @ManyToOne
    @JoinColumn(name = "robo1_id", nullable = false)
    val robo1: Robo,

    @ManyToOne
    @JoinColumn(name = "robo2_id", nullable = false)
    val robo2: Robo,

    val horarioPrevisto: LocalDateTime,

    @Enumerated(EnumType.STRING)
    var status: StatusLutaEnum = StatusLutaEnum.AGENDADA,

    // Resultados (Item h)
    @ManyToOne
    @JoinColumn(name = "vencedor_id")
    var vencedor: Robo? = null,

    var duracaoSegundos: Int? = null,
    var placarDetalhado: String? = null // Ex: JSON ou texto detalhando pontuação
)