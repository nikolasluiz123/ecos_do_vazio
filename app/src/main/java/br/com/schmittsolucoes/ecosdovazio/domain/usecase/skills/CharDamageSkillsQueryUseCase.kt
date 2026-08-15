package br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill
import br.com.schmittsolucoes.ecosdovazio.domain.repository.CharRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.PreferencesRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.SkillRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class CharDamageSkillsQueryUseCase(
    private val skillRepository: SkillRepository,
    private val userRepository: UserRepository,
    private val preferencesRepository: PreferencesRepository,
    private val charRepository: CharRepository
) {
    operator fun invoke(): Flow<List<CharSkill>> = flow {
        val userId = userRepository.getFirstUser()?.id ?: return@flow emit(emptyList())
        val charId = preferencesRepository.getUserPreferences(userId).first()?.selectedCharId ?: return@flow emit(emptyList())
        val char = charRepository.getById(charId)

        val skillsFlow = skillRepository.getCharSkills(
            classId = char.classId,
            specializationId = char.specializationId,
            categories = listOf(
                SkillCategory.DAMAGE,
                SkillCategory.DAMAGE_OVER_TIME
            )
        )

        emitAll(skillsFlow)
    }
}
