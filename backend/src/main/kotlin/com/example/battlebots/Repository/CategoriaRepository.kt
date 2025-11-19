package com.example.battlebots.Repository

import com.example.battlebots.Model.Categoria
import org.springframework.data.jpa.repository.JpaRepository

interface CategoriaRepository : JpaRepository<Categoria, Long>