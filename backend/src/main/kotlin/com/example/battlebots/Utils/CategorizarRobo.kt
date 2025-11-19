package com.example.battlebots.Utils

import com.example.battlebots.Model.Categoria
import com.example.battlebots.Model.Robo

class CategorizadorRobo {

    /**
     * Lógica para encontrar a categoria mais apropriada para um robô.
     * Esta função deve ser chamada dentro do RoboService (Model).
     * @param robo O robô a ser categorizado.
     * @param categoriasDisponiveis A lista de categorias ativas no sistema (obtidas do CategoriaRepository).
     * @return A Categoria mais adequada ou null se nenhuma for encontrada.
     */
    fun determinarCategoria(robo: Robo, categoriasDisponiveis: List<Categoria>): Categoria? {
        // Regra 1: Filtrar categorias que o robô NÃO viola.
        val categoriasCandidatas = categoriasDisponiveis.filter { categoria ->
            // Verifica limites de peso e potência (ex: o peso do robô deve ser <= limitePesoKg da categoria)
            val pesoPermitido = robo.pesoKg <= categoria.limitePesoKg
            val potenciaPermitida = robo.potenciaHp <= categoria.potenciaMaxHp

            // Verifica regras especiais (ex: se a categoria proíbe fogo, o robô não pode ter arma proibida)
            val regraEspecialAtendida = when {
                categoria.regraEspecial.contains("Proibido Armas de Fogo", ignoreCase = true) -> !robo.ehArmaProibida
                else -> true // Nenhuma regra especial ou regra especial não impede
            }

            pesoPermitido && potenciaPermitida && regraEspecialAtendida
        }

        // Regra 2: Escolher a categoria mais restritiva (ou a que o Competidor se encaixa melhor).
        // Aqui, escolhemos a de MAIOR peso (mais inclusiva) entre as que ele se qualifica.
        // Você pode ajustar esta lógica (ex: preferir a categoria com o limite de peso mais próximo do robô).
        // Exemplo: Ordem de preferência de peso crescente (para evitar categorias de peso-pena se ele for um peso-pesado).
        return categoriasCandidatas
            .sortedByDescending { it.limitePesoKg }
            .firstOrNull() // Pega a maior categoria que ele se qualifica.
    }
}