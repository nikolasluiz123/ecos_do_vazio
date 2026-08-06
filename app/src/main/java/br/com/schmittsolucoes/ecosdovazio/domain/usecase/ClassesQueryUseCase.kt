package br.com.schmittsolucoes.ecosdovazio.domain.usecase

import br.com.schmittsolucoes.ecosdovazio.domain.model.classes.ClassSelection
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.ClassRepository
import kotlinx.coroutines.flow.Flow

class ClassesQueryUseCase(
    private val classRepository: ClassRepository,
    private val languageProvider: LanguageProvider
) {
    operator fun invoke(): Flow<List<ClassSelection>> {
        return classRepository.getClassesForSelection(
            languageTag = languageProvider.getDeviceTag()
        )
    }
}
