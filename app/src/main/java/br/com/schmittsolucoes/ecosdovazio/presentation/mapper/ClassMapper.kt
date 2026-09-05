package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import br.com.schmittsolucoes.ecosdovazio.domain.model.classes.ClassSelection
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.presentation.components.models.SelectionItemUIModel
import javax.inject.Inject

class ClassMapper @Inject constructor(
    private val resourcesProvider: ResourcesProvider
) {

    fun mapToUIModel(classSelection: ClassSelection): SelectionItemUIModel {
        val presentationDrawableId = resourcesProvider.getClassImage(classSelection.presentationImageName) ?: 0
        return SelectionItemUIModel(
            id = classSelection.id,
            name = classSelection.name,
            description = classSelection.description,
            presentationDrawableId = presentationDrawableId
        )
    }
}
