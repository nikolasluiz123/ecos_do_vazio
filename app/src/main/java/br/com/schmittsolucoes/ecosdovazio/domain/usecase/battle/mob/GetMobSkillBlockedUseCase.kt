package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import javax.inject.Inject

class GetMobSkillBlockedUseCase @Inject constructor() {
    operator fun invoke(mobLevel: Long, skillMinLevel: Long): Boolean {
        return mobLevel < skillMinLevel
    }
}
