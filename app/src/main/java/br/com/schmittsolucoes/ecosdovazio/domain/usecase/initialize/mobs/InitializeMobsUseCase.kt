package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.mobs

import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.MobType
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.mobs.Mob
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.InitializeMobsResult
import br.com.schmittsolucoes.ecosdovazio.domain.model.result.InitializeMobsResult.InitializedMob
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill
import br.com.schmittsolucoes.ecosdovazio.domain.repository.MobRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.SkillRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.mobs.create.CreateCaveOrcMobUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.mobs.create.CreateGoblinHealerMobUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.mobs.create.CreateGoblinShamanMobUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.mobs.create.CreateGoblinWarriorMobUseCase

class InitializeMobsUseCase(
    private val mobRepository: MobRepository,
    private val skillRepository: SkillRepository,
    private val createGoblinWarriorMobUseCase: CreateGoblinWarriorMobUseCase,
    private val createGoblinShamanMobUseCase: CreateGoblinShamanMobUseCase,
    private val createGoblinHealerMobUseCase: CreateGoblinHealerMobUseCase,
    private val createCaveOrcMobUseCase: CreateCaveOrcMobUseCase
) {
    suspend fun executeInternal(): InitializeMobsResult {
        if (mobRepository.getExistsMob()) {
            val allMobs = mobRepository.getAllMobs()

            val initializedMobs = allMobs.mapNotNull { mob ->
                val type = when (mob.nameTranslationId) {
                    TranslationIdentifier.GOBLIN_WARRIOR_MOB_NAME -> MobType.GOBLIN_WARRIOR
                    TranslationIdentifier.GOBLIN_SHAMAN_MOB_NAME -> MobType.GOBLIN_SHAMAN
                    TranslationIdentifier.GOBLIN_HEALER_MOB_NAME -> MobType.GOBLIN_HEALER
                    TranslationIdentifier.CAVE_ORC_MOB_NAME -> MobType.CAVE_ORC
                    else -> null
                }
                type?.let { InitializedMob(mob.id, it) }
            }

            return InitializeMobsResult(initializedMobs)
        }

        val mobs = mutableListOf<Mob>()
        val skills = mutableListOf<Skill>()
        val initializedMobs = mutableListOf<InitializedMob>()

        val warriorResult = createGoblinWarriorMobUseCase.executeInternal()
        mobs.add(warriorResult.mob)
        skills.addAll(warriorResult.skills)
        initializedMobs.add(InitializedMob(warriorResult.mob.id, MobType.GOBLIN_WARRIOR))

        val shamanResult = createGoblinShamanMobUseCase.executeInternal()
        mobs.add(shamanResult.mob)
        skills.addAll(shamanResult.skills)
        initializedMobs.add(InitializedMob(shamanResult.mob.id, MobType.GOBLIN_SHAMAN))

        val healerResult = createGoblinHealerMobUseCase.executeInternal()
        mobs.add(healerResult.mob)
        skills.addAll(healerResult.skills)
        initializedMobs.add(InitializedMob(healerResult.mob.id, MobType.GOBLIN_HEALER))

        val orcResult = createCaveOrcMobUseCase.executeInternal()
        mobs.add(orcResult.mob)
        skills.addAll(orcResult.skills)
        initializedMobs.add(InitializedMob(orcResult.mob.id, MobType.CAVE_ORC))

        mobRepository.save(mobs)
        skillRepository.save(skills)

        return InitializeMobsResult(initializedMobs)
    }
}