package br.com.schmittsolucoes.ecosdovazio.presentation.mapper

import br.com.schmittsolucoes.ecosdovazio.domain.model.history.HistoryPhaseMobInfo
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.MobPhaseInfo
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.model.HistoryPhaseMobInfoUIModel
import br.com.schmittsolucoes.ecosdovazio.presentation.history.info.model.MobPhaseInfoUIModel
import javax.inject.Inject

class MobMapper @Inject constructor(
    private val resourcesProvider: ResourcesProvider,
    private val skillMapper: SkillMapper,
) {

    fun mapToUIModel(mobPhaseInfo: MobPhaseInfo): MobPhaseInfoUIModel {
        val profileImageResId = resourcesProvider.getProfileMobImage(mobPhaseInfo.mobProfileImageName) ?: 0
        return MobPhaseInfoUIModel(
            mobId = mobPhaseInfo.mobId,
            mobName = mobPhaseInfo.mobName,
            mobDescription = mobPhaseInfo.mobDescription,
            mobProfileImage = profileImageResId,
            mobCount = "${mobPhaseInfo.mobCount}x",
        )
    }

    fun mapToUIModel(historyPhaseMobInfo: HistoryPhaseMobInfo): HistoryPhaseMobInfoUIModel {
        return HistoryPhaseMobInfoUIModel(
            mobPhaseInfo = mapToUIModel(historyPhaseMobInfo.mobPhaseInfo),
            skills = historyPhaseMobInfo.skills.map(skillMapper::mapToUIModel),
        )
    }
}
