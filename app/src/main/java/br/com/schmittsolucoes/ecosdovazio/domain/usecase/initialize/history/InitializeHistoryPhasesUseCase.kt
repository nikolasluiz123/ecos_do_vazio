package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.history

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobType
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhase
import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhaseMob
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.InitializeMobsResult
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.HistoryPhaseRepository

class InitializeHistoryPhasesUseCase(
    private val historyPhaseRepository: HistoryPhaseRepository,
    private val identifierProvider: IdentifierProvider
) {
    suspend fun executeInternal(mobsResult: InitializeMobsResult) {
        val phases = listOf(
            buildPhase(
                phaseNumber = 1,
                translationId = TranslationIdentifier.FIRST_GUARD_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_WARRIOR to 1),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 2,
                translationId = TranslationIdentifier.MAGIC_AWAKENING_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_SHAMAN to 1),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 3,
                translationId = TranslationIdentifier.DOUBLE_PATROL_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_WARRIOR to 2),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 4,
                translationId = TranslationIdentifier.BLADE_AND_SPELL_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_WARRIOR to 1, MobType.GOBLIN_SHAMAN to 1),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 5,
                translationId = TranslationIdentifier.GOBLIN_AMBUSH_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_WARRIOR to 3),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 6,
                translationId = TranslationIdentifier.TACTICAL_SUPPORT_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_WARRIOR to 1, MobType.GOBLIN_HEALER to 1),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 7,
                translationId = TranslationIdentifier.HEALING_RITUAL_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_SHAMAN to 2, MobType.GOBLIN_HEALER to 1),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 8,
                translationId = TranslationIdentifier.DARK_VANGUARD_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_WARRIOR to 2, MobType.GOBLIN_SHAMAN to 1),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 9,
                translationId = TranslationIdentifier.IMMORTAL_FRONT_LINE_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_WARRIOR to 2, MobType.GOBLIN_HEALER to 2),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 10,
                translationId = TranslationIdentifier.COLOSSUS_AWAKENING_PHASE_NAME,
                mobCounts = mapOf(MobType.CAVE_ORC to 1),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 11,
                translationId = TranslationIdentifier.GIANT_AND_PRIEST_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_HEALER to 1, MobType.CAVE_ORC to 1),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 12,
                translationId = TranslationIdentifier.GOBLIN_COVEN_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_SHAMAN to 3),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 13,
                translationId = TranslationIdentifier.BALANCED_SQUAD_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_WARRIOR to 2, MobType.GOBLIN_SHAMAN to 2),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 14,
                translationId = TranslationIdentifier.BEAST_AND_MINION_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_WARRIOR to 1, MobType.CAVE_ORC to 1),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 15,
                translationId = TranslationIdentifier.FULL_BATTALION_PHASE_NAME,
                mobCounts = mapOf(
                    MobType.GOBLIN_WARRIOR to 2,
                    MobType.GOBLIN_SHAMAN to 1,
                    MobType.GOBLIN_HEALER to 1
                ),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 16,
                translationId = TranslationIdentifier.BRUTAL_MAGIC_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_SHAMAN to 2, MobType.CAVE_ORC to 1),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 17,
                translationId = TranslationIdentifier.UNCONTROLLED_FURY_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_WARRIOR to 4),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 18,
                translationId = TranslationIdentifier.BOSS_ELITE_PHASE_NAME,
                mobCounts = mapOf(
                    MobType.GOBLIN_SHAMAN to 1,
                    MobType.GOBLIN_HEALER to 1,
                    MobType.CAVE_ORC to 1
                ),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 19,
                translationId = TranslationIdentifier.RENEWING_TRENCH_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_WARRIOR to 2, MobType.GOBLIN_HEALER to 2),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 20,
                translationId = TranslationIdentifier.DOUBLE_TREMORS_PHASE_NAME,
                mobCounts = mapOf(MobType.CAVE_ORC to 2),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 21,
                translationId = TranslationIdentifier.ARCANE_CONCLAVE_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_SHAMAN to 3, MobType.GOBLIN_HEALER to 1),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 22,
                translationId = TranslationIdentifier.HEALER_GUARDIANS_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_HEALER to 1, MobType.CAVE_ORC to 2),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 23,
                translationId = TranslationIdentifier.FORCES_COLLISION_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_WARRIOR to 2, MobType.GOBLIN_SHAMAN to 2),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 24,
                translationId = TranslationIdentifier.HORDE_AND_FURY_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_WARRIOR to 3, MobType.CAVE_ORC to 1),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 25,
                translationId = TranslationIdentifier.IMMORTAL_BEAST_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_HEALER to 2, MobType.CAVE_ORC to 1),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 26,
                translationId = TranslationIdentifier.MYSTIC_STORM_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_SHAMAN to 4),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 27,
                translationId = TranslationIdentifier.WALL_OF_FLESH_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_WARRIOR to 2, MobType.CAVE_ORC to 2),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 28,
                translationId = TranslationIdentifier.ULTIMATE_BASTION_PHASE_NAME,
                mobCounts = mapOf(
                    MobType.GOBLIN_SHAMAN to 1,
                    MobType.GOBLIN_HEALER to 1,
                    MobType.CAVE_ORC to 2
                ),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 29,
                translationId = TranslationIdentifier.ORC_COUNCIL_PHASE_NAME,
                mobCounts = mapOf(MobType.CAVE_ORC to 3),
                mobsResult = mobsResult
            ),
            buildPhase(
                phaseNumber = 30,
                translationId = TranslationIdentifier.UNSTOPPABLE_TITANS_PHASE_NAME,
                mobCounts = mapOf(MobType.GOBLIN_HEALER to 2, MobType.CAVE_ORC to 2),
                mobsResult = mobsResult
            )
        )

        historyPhaseRepository.save(phases)
    }

    private fun buildPhase(
        phaseNumber: Int,
        translationId: TranslationIdentifier,
        mobCounts: Map<MobType, Int>,
        mobsResult: InitializeMobsResult
    ): HistoryPhase {
        val phaseId = identifierProvider.generate()
        val phaseMobs = mutableListOf<HistoryPhaseMob>()

        mobCounts.forEach { (type, count) ->
            val mobId = mobsResult.mobs.first { it.type == type }.id

            repeat(count) {
                phaseMobs.add(
                    HistoryPhaseMob(
                        id = identifierProvider.generate(),
                        mobId = mobId,
                        historyPhaseId = phaseId
                    )
                )
            }
        }

        return HistoryPhase(
            id = phaseId,
            nameTranslationId = translationId,
            phaseNumber = phaseNumber,
            mobs = phaseMobs
        )
    }
}