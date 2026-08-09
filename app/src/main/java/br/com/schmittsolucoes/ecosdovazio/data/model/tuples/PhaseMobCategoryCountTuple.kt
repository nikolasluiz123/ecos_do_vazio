package br.com.schmittsolucoes.ecosdovazio.data.model.tuples

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory

data class PhaseMobCategoryCountTuple(
    val historyPhaseId: String,
    val mobCategory: MobCategory,
    val count: Int
)
