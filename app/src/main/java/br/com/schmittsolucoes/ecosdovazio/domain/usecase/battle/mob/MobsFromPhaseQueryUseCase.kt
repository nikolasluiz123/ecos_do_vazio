package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.BattleMob
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.HistoryPhaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class MobsFromPhaseQueryUseCase(
    private val historyPhaseRepository: HistoryPhaseRepository,
    private val getMobLevelUseCase: GetMobLevelUseCase,
    private val getMobAttributesByLevelUseCase: GetMobAttributesByLevelUseCase,
    private val languageProvider: LanguageProvider
) {
    operator fun invoke(phaseId: String): Flow<List<BattleMob>> = flow {
        val level = getMobLevelUseCase(phaseId)
        val languageTag = languageProvider.getDeviceTag()

        val mobsFlow = historyPhaseRepository.getMobsFromPhase(phaseId, languageTag).map {
            it.map { battleMob ->
                val newAttributes = getMobAttributesByLevelUseCase.executeInternal(
                    level = level,
                    mobCategory = battleMob.mobCategory,
                    attributes = battleMob.attributes
                )

                battleMob.copy(attributes = newAttributes)
            }
        }

        emitAll(mobsFlow)
    }
}
