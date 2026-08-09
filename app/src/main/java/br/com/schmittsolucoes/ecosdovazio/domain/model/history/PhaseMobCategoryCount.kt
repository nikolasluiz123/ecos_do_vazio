package br.com.schmittsolucoes.ecosdovazio.domain.model.history

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory

data class PhaseMobCategoryCount(
    val historyPhaseId: String,
    val mobCategory: MobCategory,
    val count: Int
)
