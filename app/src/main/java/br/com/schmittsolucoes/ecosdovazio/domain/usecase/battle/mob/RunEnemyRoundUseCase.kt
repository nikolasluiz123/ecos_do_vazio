package br.com.schmittsolucoes.ecosdovazio.domain.usecase.battle.mob

class RunEnemyRoundUseCase(
    private val chooseMobSkillUseCase: ChooseMobSkillUseCase,
    private val useMobSkillUseCase: UseMobSkillUseCase,
) {
    operator fun invoke() {

    }
}