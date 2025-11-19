package com.example.battlebots.Service

import com.example.battlebots.Model.Evento
import com.example.battlebots.Model.Luta
import com.example.battlebots.Repository.EventoRepository
import com.example.battlebots.Repository.LutaRepository
import com.example.battlebots.Repository.RoboRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class EventoService(
    private val eventoRepository: EventoRepository,
    private val roboRepository: RoboRepository,
    private val lutaRepository: LutaRepository
) {
    // DTOs de exemplo...
    data class CriarEventoDTO(val nome: String, val local: String, val dataHora: LocalDateTime)

    fun criarEvento(dto: CriarEventoDTO): Evento { // Item d
        val novoEvento = Evento(nome = dto.nome, local = dto.local, dataHora = dto.dataHora)
        return eventoRepository.save(novoEvento)
    }

    fun buscarEventosAbertos(): List<Evento> { // Item e
        return eventoRepository.findAllByAbertoParaInscricaoTrue()
    }

    fun inscreverRoboEmEvento(eventoId: Long, roboId: Long): Evento {
        val evento = eventoRepository.findById(eventoId).orElseThrow { IllegalArgumentException("Evento não encontrado") }
        val robo = roboRepository.findById(roboId).orElseThrow { IllegalArgumentException("Robô não encontrado") }

        // Validação: Checar se a categoria do robô é aceita no evento (lógica mais complexa pode ser adicionada aqui)
        if (evento.abertoParaInscricao) {
            evento.robosInscritos.add(robo)
            return eventoRepository.save(evento)
        } else {
            throw IllegalStateException("Inscrições para este evento estão fechadas.")
        }
    }

    // Lógica para simular a criação de lutas com base nos robôs inscritos
    fun gerarLutas(eventoId: Long): List<Luta> {
        val evento = eventoRepository.findById(eventoId).orElseThrow { IllegalArgumentException("Evento não encontrado") }

        // Simulação de geração de chaves: pega os ímpar/par da lista
        val robos = evento.robosInscritos.toList().shuffled()
        val lutasGeradas = mutableListOf<Luta>()
        var horarioLuta = evento.dataHora.plusHours(1)

        for (i in 0 until robos.size step 2) {
            if (i + 1 < robos.size) {
                val luta = Luta(
                    evento = evento,
                    robo1 = robos[i],
                    robo2 = robos[i+1],
                    horarioPrevisto = horarioLuta
                )
                lutasGeradas.add(luta)
                horarioLuta = horarioLuta.plusMinutes(45) // Próxima luta 45 min depois
            }
        }
        return lutaRepository.saveAll(lutasGeradas)
    }

    fun buscarPlacarEvento(eventoId: Long): List<Luta> { // Item h (Placar)
        // Busca todas as lutas, incluindo agendadas e finalizadas
        return lutaRepository.findAllByEventoId(eventoId)
            .sortedBy { it.horarioPrevisto }
    }
}