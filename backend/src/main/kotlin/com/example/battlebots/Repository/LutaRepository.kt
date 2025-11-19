package com.example.battlebots.Repository

import com.example.battlebots.Enums.StatusLutaEnum
import com.example.battlebots.Model.Luta
import org.springframework.data.jpa.repository.JpaRepository

interface LutaRepository : JpaRepository<Luta, Long> {
    fun findAllByEventoId(eventoId: Long): List<Luta>
    fun findByEventoIdAndStatus(eventoId: Long, status: StatusLutaEnum): List<Luta>
}