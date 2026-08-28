package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import br.com.schmittsolucoes.ecosdovazio.domain.model.classes.ClassSelection
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.presentation.classes.selection.model.ClassSelectionUIModel
import javax.inject.Inject

class ClassMapper @Inject constructor(
    private val resourcesProvider: ResourcesProvider
) {

    fun mapToUIModel(classSelection: ClassSelection): ClassSelectionUIModel {
        val presentationDrawableId = resourcesProvider.getClassImage(classSelection.presentationImageName) ?: 0
        return ClassSelectionUIModel(
            id = classSelection.id,
            name = classSelection.name,
            description = classSelection.description,
            presentationDrawableId = presentationDrawableId
        )
    }
}
