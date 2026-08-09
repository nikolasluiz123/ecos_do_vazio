package br.com.schmittsolucoes.ecosdovazio.domain.model.result

import br.com.schmittsolucoes.ecosdovazio.domain.model.classes.Class
import br.com.schmittsolucoes.ecosdovazio.domain.model.skills.Skill

data class CreateClassResult(
    val classModel: Class,
    val skills: List<Skill>
)