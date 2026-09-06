package br.com.schmittsolucoes.ecosdovazio.domain.provider

interface ResourcesProvider {
    fun getClassImage(name: String): Int?
    fun getSpecializationImage(name: String): Int?
    fun getCharBattleImage(name: String): Int?
    fun getCharProfileImage(name: String): Int?
    fun getBattleMobImage(name: String): Int?
    fun getProfileMobImage(name: String): Int?
    fun getSkillImage(name: String): Int?
    fun getPhaseImage(name: String): Int?
    fun getCoatArmsImage(name: String): Int?
}
