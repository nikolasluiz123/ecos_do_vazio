package br.com.schmittsolucoes.ecosdovazio.data.provider

import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.domain.provider.ResourcesProvider
import javax.inject.Inject

const val WARRIOR_CLASS_IMAGE_KEY = "classe_guerreiro"
const val MAGE_CLASS_IMAGE_KEY = "classe_mago"
const val ARCHER_CLASS_IMAGE_KEY = "classe_arqueiro"

const val BATTLE_IMAGE_WARRIOR_CLASS_IMAGE_KEY = "classe_guerreiro_16_9"
const val BATTLE_IMAGE_MAGE_CLASS_IMAGE_KEY = "classe_mago_16_9"
const val BATTLE_IMAGE_ARCHER_CLASS_IMAGE_KEY = "classe_arqueiro_16_9"

class AndroidResourcesProvider @Inject constructor() : ResourcesProvider {
    override fun getClassImage(name: String): Int? {
        return when (name) {
            ARCHER_CLASS_IMAGE_KEY -> R.drawable.classe_arqueiro
            WARRIOR_CLASS_IMAGE_KEY -> R.drawable.classe_guerreiro
            MAGE_CLASS_IMAGE_KEY -> R.drawable.classe_mago
            else -> null
        }
    }
}
