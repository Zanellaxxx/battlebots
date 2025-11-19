package com.example.battlebots.Model

import com.example.battlebots.Enums.TipoUsuarioEnum
import jakarta.persistence.*

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Usando JOINED para herança
abstract class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long = 0,

    @Column(unique = true, nullable = false)
    open val username: String,

    @Column(nullable = false)
    open val senha: String, // Em um projeto real, esta senha deve ser Hash!

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    open val tipoUsuario: TipoUsuarioEnum
)