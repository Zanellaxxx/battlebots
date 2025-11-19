package com.example.battlebots.Service

import com.example.battlebots.Model.Competidor
import com.example.battlebots.Model.Usuario
import com.example.battlebots.DTO.RegistroCompetidorDTO
import com.example.battlebots.Repository.CompetidorRepository
import com.example.battlebots.Repository.UsuarioRepository
import org.springframework.stereotype.Service

@Service
class UsuarioService(
    private val usuarioRepository: UsuarioRepository,
    private val competidorRepository: CompetidorRepository
) {

    fun registrarCompetidor(dto: RegistroCompetidorDTO): Competidor {

        if (usuarioRepository.findByUsername(dto.username).isPresent) {
            throw IllegalArgumentException("Username já cadastrado.")
        }

        val novoCompetidor = Competidor(
            nomeCompleto = dto.nomeCompleto,
            email = dto.email,
            username = dto.username,
            senha = dto.senha,
        )
        return competidorRepository.save(novoCompetidor)
    }

    fun buscarPorUsername(username: String): Usuario? {
        return usuarioRepository.findByUsername(username).orElse(null)
    }
}