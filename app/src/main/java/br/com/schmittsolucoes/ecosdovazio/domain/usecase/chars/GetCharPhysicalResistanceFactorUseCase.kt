package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

class GetCharPhysicalResistanceFactorUseCase {
    fun executeInternal(category: ClassCategory): Double {
        return when (category) {
            ClassCategory.WARRIOR -> 2.0
            ClassCategory.MAGE -> 0.5
            ClassCategory.ARCHER -> 1.0
        }
    }
}
