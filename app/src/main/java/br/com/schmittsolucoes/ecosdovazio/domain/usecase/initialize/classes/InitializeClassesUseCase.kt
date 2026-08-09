package br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes

import br.com.schmittsolucoes.ecosdovazio.domain.model.classes.Class
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill
import br.com.schmittsolucoes.ecosdovazio.domain.model.specialization.Specialization
import br.com.schmittsolucoes.ecosdovazio.domain.repository.ClassRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.SkillRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.SpecializationRepository
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.archer.CreateArcherClassUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.archer.CreateBeastMasterSpecializationUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.archer.CreateEngineerSpecializationUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.mage.CreateFireMageSpecializationUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.mage.CreateMageClassUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.mage.CreateWaterMageSpecializationUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.warrior.CreateGladiatorSpecializationUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.warrior.CreateGuardianSpecializationUseCase
import br.com.schmittsolucoes.ecosdovazio.domain.usecase.initialize.classes.create.warrior.CreateWarriorClassUseCase

class InitializeClassesUseCase(
    private val classRepository: ClassRepository,
    private val specializationRepository: SpecializationRepository,
    private val skillRepository: SkillRepository,
    private val createWarriorClassUseCase: CreateWarriorClassUseCase,
    private val createGuardianSpecializationUseCase: CreateGuardianSpecializationUseCase,
    private val createGladiatorSpecializationUseCase: CreateGladiatorSpecializationUseCase,
    private val createMageClassUseCase: CreateMageClassUseCase,
    private val createFireMageSpecializationUseCase: CreateFireMageSpecializationUseCase,
    private val createWaterMageSpecializationUseCase: CreateWaterMageSpecializationUseCase,
    private val createArcherClassUseCase: CreateArcherClassUseCase,
    private val createEngineerSpecializationUseCase: CreateEngineerSpecializationUseCase,
    private val createBeastMasterSpecializationUseCase: CreateBeastMasterSpecializationUseCase,
) {

    suspend fun executeInternal() {
        if (!classRepository.getExistsClass()) {
            val classes = mutableListOf<Class>()
            val specializations = mutableListOf<Specialization>()
            val skills = mutableListOf<Skill>()

            val warriorResult = createWarriorClassUseCase.executeInternal()
            classes.add(warriorResult.classModel)
            skills.addAll(warriorResult.skills)

            val guardianResult = createGuardianSpecializationUseCase.executeInternal(warriorResult.classModel.id)
            specializations.add(guardianResult.specialization)
            skills.addAll(guardianResult.skills)

            val gladiatorResult = createGladiatorSpecializationUseCase.executeInternal(warriorResult.classModel.id)
            specializations.add(gladiatorResult.specialization)
            skills.addAll(gladiatorResult.skills)

            val mageResult = createMageClassUseCase.executeInternal()
            classes.add(mageResult.classModel)
            skills.addAll(mageResult.skills)

            val fireMageResult = createFireMageSpecializationUseCase.executeInternal(mageResult.classModel.id)
            specializations.add(fireMageResult.specialization)
            skills.addAll(fireMageResult.skills)

            val waterMageResult = createWaterMageSpecializationUseCase.executeInternal(mageResult.classModel.id)
            specializations.add(waterMageResult.specialization)
            skills.addAll(waterMageResult.skills)

            val archerResult = createArcherClassUseCase.executeInternal()
            classes.add(archerResult.classModel)
            skills.addAll(archerResult.skills)

            val engineerResult = createEngineerSpecializationUseCase.executeInternal(archerResult.classModel.id)
            specializations.add(engineerResult.specialization)
            skills.addAll(engineerResult.skills)

            val beastMasterResult = createBeastMasterSpecializationUseCase.executeInternal(archerResult.classModel.id)
            specializations.add(beastMasterResult.specialization)
            skills.addAll(beastMasterResult.skills)

            classRepository.save(classes)
            specializationRepository.save(specializations)
            skillRepository.save(skills)
        }
    }
}
