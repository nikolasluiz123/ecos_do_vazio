package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.MobSkillUsageResult
import br.com.schmittsolucoes.ecosdovazio.domain.mapper.toInfo
import br.com.schmittsolucoes.ecosdovazio.domain.mapper.toUsedInfo
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class RunEnemyRoundUseCase(
    private val chooseMobSkillUseCase: ChooseMobSkillUseCase,
    private val useMobSkillUseCase: UseMobSkillUseCase,
) {
    suspend operator fun invoke(
        getCharInfo: () -> BattleCharInfo,
        mobs: List<BattleMob>,
        onMobUseSkill: (MobSkillUsageResult) -> Unit,
    ) {
        val livingMobs = mobs.filter { it.actualHealth > 0 }

        livingMobs.forEach { mob ->
            chooseMobSkillUseCase.executeInternal(mob.skills)?.let { skill ->
                val usageResult = useMobSkillUseCase.executeInternal(
                    skillInfo = skill.toUsedInfo(),
                    battleMobInfo = mob.toInfo(),
                    battleCharInfo = getCharInfo()
                )

                onMobUseSkill(usageResult)
                delay(300.milliseconds)
            }
        }
    }
}