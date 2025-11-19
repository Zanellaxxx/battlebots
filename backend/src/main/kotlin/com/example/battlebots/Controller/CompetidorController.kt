package com.example.battlebots.Controller

import com.example.battlebots.Service.EventoService
import com.example.battlebots.Service.RoboService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api/competidor")
// Todos os endpoints aqui geralmente exigirão que o usuário esteja autenticado.
class CompetidorController(
    private val roboService: RoboService,
    private val eventoService: EventoService
) {
    // Simulação de DTO de Registro
    data class RegistrarRoboRequest(
        val nome: String,
        val pesoKg: Double,
        val potenciaHp: Int,
        val armamentoPrincipal: String,
        val ehArmaProibida: Boolean
    )

    // Item c: Competidores podem registrar os seus robôs.
    // Usamos @PreAuthorize e @AuthenticationPrincipal para obter o ID do usuário logado.
    @PreAuthorize("hasRole('COMPETIDOR')")
    @PostMapping("/robos")
    fun registrarRobo(
        @AuthenticationPrincipal userDetails: UserDetails,
        @RequestBody request: RegistrarRoboRequest
    ): ResponseEntity<*> {
        // **Nota:** No seu sistema de segurança, o usernameDetails deve fornecer o ID do Competidor
        val competidorId = 1L // ID real deve ser obtido do token/security context

        return try {
            val robo = roboService.registrarRobo(
                competidorId,
                RoboService.RegistroRoboDTO(
                    request.nome, request.pesoKg, request.potenciaHp, request.armamentoPrincipal, request.ehArmaProibida
                )
            )
            ResponseEntity.ok(robo)
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }

    // Item c: Acessar robôs previamente cadastrados
    @("hasRole('COMPETIDOR')")
    @GetMapping("/robos")
    fun getMeusRobos(@AuthenticationPrincipal userDetails: UserDetails): ResponseEntity<*> {
        val competidorId = 1L // ID real
        val robos = roboService.buscarRobosDoCompetidor(competidorId)
        return ResponseEntity.ok(robos)
    }

    // Item c: Competidores podem se inscrever em competições
    @PreAuthorize("hasRole('COMPETIDOR')")
    @PostMapping("/eventos/{eventoId}/inscricao/{roboId}")
    fun inscreverEmEvento(
        @PathVariable eventoId: Long,
        @PathVariable roboId: Long,
        @AuthenticationPrincipal userDetails: UserDetails
    ): ResponseEntity<*> {
        // Validação adicional: Garantir que o roboId pertence ao usuário logado
        return try {
            val eventoAtualizado = eventoService.inscreverRoboEmEvento(eventoId, roboId)
            ResponseEntity.ok(
                mapOf(
                    "mensagem" to "Inscrição realizada com sucesso!",
                    "evento" to eventoAtualizado.nome
                )
            )
        } catch (e: Exception) {
            ResponseEntity.badRequest().body(mapOf("erro" to e.message))
        }
    }

    // Outros: PUT e DELETE para robôs (Item c)
}