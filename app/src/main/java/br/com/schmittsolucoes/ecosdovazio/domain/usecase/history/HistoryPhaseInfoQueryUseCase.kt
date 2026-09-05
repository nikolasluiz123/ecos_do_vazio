package br.com.schmittsolucoes.ecosdovazio.domain.usecase.history

import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhaseMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.HistoryPhaseRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.SkillRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class HistoryPhaseInfoQueryUseCase(
    private val historyPhaseRepository: HistoryPhaseRepository,
    private val skillRepository: SkillRepository,
    private val languageProvider: LanguageProvider,
) {
    operator fun invoke(phaseId: String): Flow<List<HistoryPhaseMobInfo>> = flow {
        val languageTag = languageProvider.getDeviceTag()
        val mobsInfo = historyPhaseRepository.getPhaseMobsInfo(phaseId, languageTag)

        val result = coroutineScope {
            mobsInfo.map { mobPhaseInfo ->
                async {
                    val skills = skillRepository.getMobSkills(languageTag, mobPhaseInfo.mobId)

                    HistoryPhaseMobInfo(
                        mobPhaseInfo = mobPhaseInfo,
                        skills = skills,
                    )
                }
            }.awaitAll()
        }

        emit(result)
    }
}
