package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import br.com.schmittsolucoes.ecosdovazio.domain.model.specialization.SpecializationSelection
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.presentation.components.models.SelectionItemUIModel
import javax.inject.Inject

class SpecializationMapper @Inject constructor(
    private val resourcesProvider: ResourcesProvider
) {

    fun mapToUIModel(specializationSelection: SpecializationSelection): SelectionItemUIModel {
        val presentationDrawableId = resourcesProvider.getSpecializationImage(specializationSelection.presentationImageName) ?: 0

        return SelectionItemUIModel(
            id = specializationSelection.id,
            name = specializationSelection.name,
            description = specializationSelection.description,
            presentationDrawableId = presentationDrawableId
        )
    }
}
