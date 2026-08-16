package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

class GetCharMagicResistanceFactorUseCase {
    fun executeInternal(category: ClassCategory): Double {
        return when (category) {
            ClassCategory.WARRIOR -> 0.8
            ClassCategory.MAGE -> 2.0
            ClassCategory.ARCHER -> 1.0
        }
    }
}
