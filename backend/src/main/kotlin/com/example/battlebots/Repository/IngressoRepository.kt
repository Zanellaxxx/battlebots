package com.example.battlebots.Repository

import com.example.battlebots.Model.Ingresso
import org.springframework.data.jpa.repository.JpaRepository

interface IngressoRepository : JpaRepository<Ingresso, Long> {
    fun findAllByEventoId(eventoId: Long): List<Ingresso>
}