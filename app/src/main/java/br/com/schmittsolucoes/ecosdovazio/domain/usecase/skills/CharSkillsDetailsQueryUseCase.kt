package br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills

import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkillDetails
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.SkillRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.chars.GetCharBattleUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class CharSkillsDetailsQueryUseCase(
    private val skillRepository: SkillRepository,
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository,
    private val charRepository: CharRepository,
    private val languageProvider: LanguageProvider,
    private val getCharSkillBlockedUseCase: GetCharSkillBlockedUseCase,
    private val getCharBattleUseCase: GetCharBattleUseCase,
) {
    operator fun invoke(): Flow<List<CharSkillDetails>> = flow {
        val userId = userRepository.getFirstUser()?.id ?: return@flow emit(emptyList())
        val charId = preferencesRepository.getUserPreferences(userId).first()?.selectedCharId ?: return@flow emit(emptyList())
        val char = charRepository.getById(charId)

        val battleCharFlow = getCharBattleUseCase()

        val skillsFlow = skillRepository.getAllSkills(
            languageTag = languageProvider.getDeviceTag(),
            classId = char.classId,
            specializationId = char.specializationId
        )

        val combinedFlow = combine(skillsFlow, battleCharFlow) { skills, battleChar ->
            skills.map { skill ->
                skill.copy(
                    blocked = getCharSkillBlockedUseCase(
                        battleChar = battleChar,
                        skillRequiredAttributes = skill.attributes,
                        minLevel = skill.minLevel
                    )
                )
            }
        }

        emitAll(combinedFlow)
    }
}
