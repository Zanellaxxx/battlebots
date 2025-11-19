package com.example.battlebots.Controller

import com.example.battlebots.Repository.RoboRepository
import com.example.battlebots.Service.EventoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/publico")
class PublicController(
    private val eventoService: EventoService,
    private val roboRepository: RoboRepository // Usamos o repository diretamente para leituras simples
) {
    // Item e: Evento de combate deve ser aberto para o público.
    @GetMapping("/eventos")
    fun getEventosAbertos(): ResponseEntity<*> {
        val eventos = eventoService.buscarEventosAbertos()
        return ResponseEntity.ok(eventos)
    }

    // Item e/h: Mostrar informações do evento, regras, etc.
    @GetMapping("/eventos/{id}")
    fun getDetalhesEvento(@PathVariable id: Long): ResponseEntity<*> {
        // Você deve criar um DTO para retornar detalhes completos do evento (lutas, ingressos, regras)
        return ResponseEntity.ok(mapOf("detalhes" to "Evento $id detalhes"))
    }

    // Item h: Mostrar o placar, horários das lutas, resultados, etc. (A cereja do bolo).
    @GetMapping("/eventos/{id}/placar")
    fun getPlacarEvento(@PathVariable id: Long): ResponseEntity<*> {
        val placar = eventoService.buscarPlacarEvento(id)
        return ResponseEntity.ok(placar)
    }

    // Item h: Descrição de cada robô e a equipe.
    @GetMapping("/robos/{id}")
    fun getRoboDetalhes(@PathVariable id: Long): ResponseEntity<*> {
        val robo = roboRepository.findById(id).orElse(null)
        if (robo == null) {
            return ResponseEntity.notFound().build<Any>()
        }
        // Retorna informações públicas do robô e do competidor (equipe)
        return ResponseEntity.ok(robo)
    }
}