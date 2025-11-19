package com.example.battlebots.Service

import com.example.battlebots.Model.Categoria
import com.example.battlebots.Repository.CategoriaRepository
import org.springframework.stereotype.Service

@Service
class CategoriaService(
    private val categoriaRepository: CategoriaRepository
) {
    // DTOs de exemplo...
    data class NovaCategoriaDTO(val nome: String, val descricao: String, val limitePesoKg: Double, val potenciaMaxHp: Int, val regraEspecial: String)

    fun criarCategoria(dto: NovaCategoriaDTO): Categoria {
        // Lógica de validação... (Apenas Admin pode criar, verificado na Controller)
        val novaCategoria = Categoria(
            nome = dto.nome,
            descricao = dto.descricao,
            limitePesoKg = dto.limitePesoKg,
            potenciaMaxHp = dto.potenciaMaxHp,
            regraEspecial = dto.regraEspecial
        )
        return categoriaRepository.save(novaCategoria)
    }

    fun buscarTodas(): List<Categoria> {
        return categoriaRepository.findAll()
    }
}