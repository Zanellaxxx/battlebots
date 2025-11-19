package com.example.battlebots.Controller

import com.example.battlebots.Service.CategoriaService
import com.example.battlebots.Service.EventoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
@RequestMapping("/api/admin")
class AdminController(
    private val eventoService: EventoService,
    private val categoriaService: CategoriaService
) {
    // Simulação de DTO de Evento
    data class CriarEventoRequest(val nome: String, val local: String, val dataHora: String)
    data class CriarCategoriaRequest(val nome: String, val descricao: String, val limitePesoKg: Double, val potenciaMaxHp: Int, val regraEspecial: String)

    // Item d: Administradores podem criar um evento de combate.
    // Requer a role ADMIN
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/eventos")
    fun criarEvento(@RequestBody request: CriarEventoRequest): ResponseEntity<*> {
        return try {
            val evento = eventoService.criarEvento(
                EventoService.CriarEventoDTO(
                    request.nome,
                    request.local,
                    LocalDateTime.parse(request.dataHora) // Converter string para LocalDateTime
                )
            )
            ResponseEntity.ok(evento)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }

    // Item f: Criar categorias de combate.
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/categorias")
    fun criarCategoria(@RequestBody request: CriarCategoriaRequest): ResponseEntity<*> {
        val categoria = categoriaService.criarCategoria(
            CategoriaService.NovaCategoriaDTO(
                request.nome, request.descricao, request.limitePesoKg, request.potenciaMaxHp, request.regraEspecial
            )
        )
        return ResponseEntity.ok(categoria)
    }

    // Endpoint para simular a criação das chaves de luta
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/eventos/{eventoId}/gerar-lutas")
    fun gerarLutas(@PathVariable eventoId: Long): ResponseEntity<*> {
        return try {
            val lutas = eventoService.gerarLutas(eventoId)
            ResponseEntity.ok(mapOf("mensagem" to "Lutas geradas com sucesso", "totalLutas" to lutas.size))
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }

    // Outros: Vender ingressos (Item d), avaliar pedidos de competidores (Item d), etc. fazer se sobrar tempo.
}