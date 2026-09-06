package br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.SkillRepository
import kotlinx.coroutines.flow.Flow

class ClassSkillsQueryUseCase(
    private val skillRepository: SkillRepository,
    private val languageProvider: LanguageProvider,
) {
    operator fun invoke(classId: String): Flow<List<CharSkill>> {
        return skillRepository.getCharSkills(
            languageTag = languageProvider.getDeviceTag(),
            classId = classId,
            specializationId = null,
            categories = SkillCategory.entries
        )
    }
}
