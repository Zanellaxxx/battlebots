package com.example.battlebots.DTO

data class NovaCategoriaDTO(
    val nome: String,
    val descricao: String,
    val limitePesoKg: Double,
    val potenciaMaxHp: Int,
    val regraEspecial: String
)