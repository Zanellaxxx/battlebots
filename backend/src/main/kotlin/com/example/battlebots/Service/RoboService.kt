package com.example.battlebots.Service

import com.example.battlebots.Model.Robo
import com.example.battlebots.Repository.CompetidorRepository
import com.example.battlebots.Repository.RoboRepository
import com.example.battlebots.Utils.CategorizadorRobo
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.stereotype.Service

@Service
class RoboService(
    private val roboRepository: RoboRepository,
    private val competidorRepository: CompetidorRepository,
    private val categoriaService: CategoriaService
) {
    private val categorizador = CategorizadorRobo()

    // DTOs de exemplo...
    data class RegistroRoboDTO(val nome: String, val pesoKg: Double, val potenciaHp: Int, val armamentoPrincipal: String, val ehArmaProibida: Boolean)

    fun registrarRobo(competidorId: Long, dto: RegistroRoboDTO): Robo {
        val competidor = competidorRepository.findById(competidorId)
            .orElseThrow { ChangeSetPersister.NotFoundException() }

        var novoRobo = Robo(
            nome = dto.nome,
            pesoKg = dto.pesoKg,
            potenciaHp = dto.potenciaHp,
            armamentoPrincipal = dto.armamentoPrincipal,
            ehArmaProibida = dto.ehArmaProibida,
            competidor = competidor
        )

        // **A cereja do bolo:** Lógica de categorização automática (Item g)
        val categorias = categoriaService.buscarTodas()
        val categoriaDeterminada = categorizador.determinarCategoria(novoRobo, categorias)

        if (categoriaDeterminada == null) {
            // Se não couber em nenhuma, o robô não pode ser registrado.
            throw IllegalStateException("O robô não se enquadra em nenhuma categoria de combate ativa.")
        }

        novoRobo.categoria = categoriaDeterminada
        return roboRepository.save(novoRobo)
    }

    fun buscarRobosDoCompetidor(competidorId: Long): List<Robo> {
        return roboRepository.findAllByCompetidorId(competidorId)
    }

    // Outros métodos: editarRobo, excluirRobo (com validação de propriedade)
}