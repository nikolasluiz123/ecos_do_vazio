package br.com.schmittsolucoes.ecosdovazio.domain.provider

interface ResourcesProvider {
    fun getClassImage(name: String): Int?
    fun getSpecializationImage(name: String): Int?
    fun getBattleClassImage(name: String): Int?
    fun getBattleSpecializationImage(name: String): Int?
    fun getProfileClassImage(name: String): Int?
    fun getProfileSpecializationImage(name: String): Int?
    fun getBattleMobImage(name: String): Int?
    fun getSkillImage(name: String): Int?
    fun getPhaseImage(name: String): Int?
    fun getCoatArmsImage(name: String): Int?
}
