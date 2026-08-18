package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.MobSkill
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.HistoryPhaseRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.SkillRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MobsFromPhaseQueryUseCase @Inject constructor(
    private val historyPhaseRepository: HistoryPhaseRepository,
    private val skillRepository: SkillRepository,
    private val getMobLevelUseCase: GetMobLevelUseCase,
    private val getMobAttributesByLevelUseCase: GetMobAttributesByLevelUseCase,
    private val getMobSkillBlockedUseCase: GetMobSkillBlockedUseCase,
    private val languageProvider: LanguageProvider
) {
    operator fun invoke(phaseId: String): Flow<List<BattleMob>> {
        val languageTag = languageProvider.getDeviceTag()

        return historyPhaseRepository.getMobsFromPhase(phaseId, languageTag).map { mobs ->
            val level = getMobLevelUseCase(phaseId)
            val mappedMobs = mutableListOf<BattleMob>()

            for (battleMob in mobs) {
                val newAttributes = getMobAttributesByLevelUseCase.executeInternal(
                    level = level,
                    mobCategory = battleMob.mobCategory,
                    attributes = battleMob.attributes
                )

                val skills = skillRepository.getMobSkills(battleMob.id).map { skill ->
                    val isBlocked = getMobSkillBlockedUseCase(level, skill.minLevel)

                    when (skill) {
                        is MobSkill.CommonDamage -> skill.copy(blocked = isBlocked)
                        is MobSkill.DamageOverTime -> skill.copy(blocked = isBlocked)
                        is MobSkill.Buff -> skill.copy(blocked = isBlocked)
                        is MobSkill.Debuff -> skill.copy(blocked = isBlocked)
                    }
                }

                mappedMobs.add(
                    battleMob.copy(
                        level = level,
                        attributes = newAttributes,
                        skills = skills
                    )
                )
            }

            mappedMobs
        }
    }
}
