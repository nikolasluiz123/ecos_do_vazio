package br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.SkillRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class CharBuffSkillsQueryUseCase(
    private val skillRepository: SkillRepository,
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository,
    private val charRepository: CharRepository,
    private val languageProvider: LanguageProvider,
) {
    operator fun invoke(): Flow<List<CharSkill>> = flow {
        val userId = userRepository.getFirstUser()?.id ?: return@flow emit(emptyList())
        val charId = preferencesRepository.getUserPreferences(userId).first()?.selectedCharId ?: return@flow emit(emptyList())
        val char = charRepository.getById(charId)

        val skillsFlow = skillRepository.getCharSkills(
            languageTag = languageProvider.getDeviceTag(),
            classId = char.classId,
            specializationId = char.specializationId,
            categories = listOf(
                SkillCategory.OFFENSIVE_BUFF,
                SkillCategory.DEFENSIVE_BUFF,
            )
        )

        emitAll(skillsFlow)
    }
}
