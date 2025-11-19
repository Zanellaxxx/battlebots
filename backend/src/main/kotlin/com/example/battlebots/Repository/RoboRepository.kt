package com.example.battlebots.Repository

import com.example.battlebots.Model.Robo
import org.springframework.data.jpa.repository.JpaRepository

interface RoboRepository : JpaRepository<Robo, Long> {
    fun findAllByCompetidorId(competidorId: Long): List<Robo>
}
