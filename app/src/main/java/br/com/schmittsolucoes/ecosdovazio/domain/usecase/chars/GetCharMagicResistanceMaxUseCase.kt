package br.com.schmittsolucoes.ecosdovazio.domain.usecase.chars

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory

class GetCharMagicResistanceMaxUseCase {
    fun executeInternal(category: ClassCategory): Double {
        return when (category) {
            ClassCategory.WARRIOR -> 0.4
            ClassCategory.MAGE -> 0.75
            ClassCategory.ARCHER -> 0.5
        }
    }
}
