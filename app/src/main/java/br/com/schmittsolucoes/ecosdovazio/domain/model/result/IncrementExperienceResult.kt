package br.com.schmittsolucoes.ecosdovazio.domain.model.result

data class IncrementExperienceResult(
    val newLevel: Long,
    val newExperience: Long,
    val levelUp: Boolean
)
