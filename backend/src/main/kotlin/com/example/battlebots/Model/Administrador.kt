package com.example.battlebots.Model

import com.example.battlebots.Enums.TipoUsuarioEnum
import jakarta.persistence.Entity
//roles na hora que eu fizer a autenticação utilizar o spring authentication
@Entity
class Administrador(
    val nome: String,

    // Propriedades herdadas de Usuario
    override val username: String,
    override val senha: String
) : Usuario(
    username = username,
    senha = senha,
    tipoUsuario = TipoUsuarioEnum.ADMIN
)