package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
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

                val skills = skillRepository.getMobSkills(battleMob.id)

                mappedMobs.add(
                    battleMob.copy(
                        attributes = newAttributes,
                        skills = skills
                    )
                )
            }

            mappedMobs
        }
    }
}
