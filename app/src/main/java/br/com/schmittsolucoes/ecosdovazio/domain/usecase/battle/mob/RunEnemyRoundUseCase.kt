package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.mapper.toInfo
import br.com.schmittsolucoes.ecosdovazio.domain.mapper.toUsedInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.chars.BattleCharInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.MobSkillUsageResult
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

class RunEnemyRoundUseCase(
    private val chooseMobSkillUseCase: ChooseMobSkillUseCase,
    private val useMobSkillUseCase: UseMobSkillUseCase,
) {
    suspend operator fun invoke(
        getCharInfo: () -> BattleCharInfo,
        mobs: List<BattleMob>,
        onMobUseSkill: (mob: BattleMob, result: MobSkillUsageResult) -> Unit,
    ) {
        val livingMobs = mobs.filter { it.actualHealth > 0 }

        livingMobs.forEach { mob ->
            chooseMobSkillUseCase.executeInternal(skills = mob.skills, liveMobs = livingMobs)?.let { skill ->
                val usageResult = useMobSkillUseCase.executeInternal(
                    skillInfo = skill.toUsedInfo(),
                    battleMobInfo = mob.toInfo(),
                    battleCharInfo = getCharInfo(),
                    liveMobs = livingMobs.map { it.toInfo() },
                )

                onMobUseSkill(mob, usageResult)
                delay(1.seconds)
            }
        }
    }
}