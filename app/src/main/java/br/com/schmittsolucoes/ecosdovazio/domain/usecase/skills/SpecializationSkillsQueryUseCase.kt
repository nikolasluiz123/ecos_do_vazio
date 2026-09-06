package br.com.schmittsolucoes.ecosdovazio.domain.usecase.skills

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.SkillCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.CharSkill
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.SkillRepository
import kotlinx.coroutines.flow.Flow

class SpecializationSkillsQueryUseCase(
    private val skillRepository: SkillRepository,
    private val languageProvider: LanguageProvider,
) {
    operator fun invoke(specializationId: String): Flow<List<CharSkill>> {
        return skillRepository.getCharSkills(
            languageTag = languageProvider.getDeviceTag(),
            classId = null,
            specializationId = specializationId,
            categories = SkillCategory.entries,
        )
    }
}
