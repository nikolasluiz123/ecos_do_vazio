package br.com.schmittsolucoes.ecosdovazio.domain.model.chars

data class Char(
    val id: String,
    val name: String,
    val classId: String,
    val userId: String,
    val experience: Long = 0,
    val specializationId: String? = null,
    val level: Long = 1,
    val strength: Long = 0,
    val dexterity: Long = 0,
    val intelligence: Long = 0,
    val physicalResistance: Long = 0,
    val magicResistance: Long = 0,
    val vitality: Long = 0,
    val agility: Long = 0,
)