package com.example.battlebots.Model

import com.example.battlebots.Enums.TipoUsuarioEnum
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
class Competidor(
    @Column(nullable = false)
    val nomeCompleto: String,

    @Column(unique = true)
    val email: String,

    // Relacionamento com Robôs (Um competidor pode ter N robôs)
    @OneToMany(mappedBy = "competidor", cascade = [CascadeType.ALL], orphanRemoval = true)
    val robos: MutableList<Robo> = mutableListOf(),

    // Propriedades herdadas de Usuario
    override val username: String,
    override val senha: String
) : Usuario(
    username = username,
    senha = senha,
    tipoUsuario = TipoUsuarioEnum.COMPETIDOR
)