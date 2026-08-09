package br.com.schmittsolucoes.ecosdovazio.domain.usecase.history

import br.com.schmittsolucoes.ecosdovazio.data.provider.*
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.CharHistoryPhase
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.PhaseMobCategoryCount
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.HistoryPhaseRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class HistoryPhasesQueryUseCase(
    private val historyPhaseRepository: HistoryPhaseRepository,
    private val preferencesRepository: PreferencesRepository,
    private val userRepository: UserRepository,
    private val languageProvider: LanguageProvider
) {
    operator fun invoke(): Flow<List<CharHistoryPhase>> = flow {
        val userId = userRepository.getFirstUser()?.id

        if (userId.isNullOrBlank()) {
            emit(emptyList())
            return@flow
        }

        val charId = preferencesRepository.getUserPreferences(userId).first()?.selectedCharId

        if (charId.isNullOrBlank()) {
            emit(emptyList())
            return@flow
        }

        val mobCounts = historyPhaseRepository.getMobCategoryCountsPerPhase()
        val countsByPhase = mobCounts.groupBy { it.historyPhaseId }

        val phasesFlow = historyPhaseRepository.getPhases(
            charId = charId,
            languageTag = languageProvider.getDeviceTag()
        ).map { phases ->
            phases.map { phase ->
                val phaseCounts = countsByPhase[phase.phaseId] ?: emptyList()
                phase.copy(imageName = getPhaseImageName(phaseCounts))
            }
        }

        emitAll(phasesFlow)
    }

    private fun getPhaseImageName(counts: List<PhaseMobCategoryCount>): String {
        if (counts.isEmpty()) return PHASE_CRACKED_SHIELD_ICON_KEY

        val totalMobs = counts.sumOf { it.count }
        val hasOrc = counts.any { it.mobCategory == MobCategory.ORC_WARRIOR }
        val hasHealer = counts.any { it.mobCategory == MobCategory.HEALER }
        val mageCount = counts.find { it.mobCategory == MobCategory.MAGE }?.count ?: 0
        val warriorCount = counts.find { it.mobCategory == MobCategory.WARRIOR }?.count ?: 0

        return when {
            hasOrc -> PHASE_GIANT_CLUB_ICON_KEY
            hasHealer -> PHASE_HEART_WITH_AURA_ICON_KEY
            mageCount > totalMobs / 2 -> PHASE_PURPLE_FLAME_ICON_KEY
            warriorCount == totalMobs -> PHASE_CHIPPED_SWORD_ICON_KEY
            else -> PHASE_CRACKED_SHIELD_ICON_KEY
        }
    }
}
