package com.example.battlebots.Controller

import com.example.battlebots.DTO.RegistroCompetidorDTO
import com.example.battlebots.Service.UsuarioService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auth")
class AuthController(private val usuarioService: UsuarioService) {

    // Item c: Competidores podem criar uma conta
    @PostMapping("/cadastro/competidor")
    fun registrarCompetidor(@RequestBody request: RegistroCompetidorDTO): ResponseEntity<*> {
        return try {
            val competidor = usuarioService.registrarCompetidor(request)
            // Retorna 201 Created
            ResponseEntity.ok(competidor)
        } catch (e: IllegalArgumentException) {
            // Em caso de username duplicado
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }

    // O Endpoint de Login seria implementado aqui, dependendo da sua estratégia de segurança (JWT/OAuth2).
    // @PostMapping("/login") ...
}