package com.example.battlebots.Repository

import com.example.battlebots.Model.Evento
import org.springframework.data.jpa.repository.JpaRepository

interface EventoRepository : JpaRepository<Evento, Long> {
    fun findAllByAbertoParaInscricaoTrue(): List<Evento>
}