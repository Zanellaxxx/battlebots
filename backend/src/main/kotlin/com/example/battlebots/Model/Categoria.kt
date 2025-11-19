package com.example.battlebots.Model

import jakarta.persistence.*

@Entity
class Categoria(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false, unique = true)
    val nome: String, // Ex: Peso-Pesado, Featherweight, Sem-Fogo

    val descricao: String,

    // Regras que determinam o enquadramento do robô:
    val limitePesoKg: Double, // Ex: 1000.0 (1 Tonelada)
    val potenciaMaxHp: Int,
    val regraEspecial: String // Ex: "Proibido Armas de Fogo e Líquidas"
)