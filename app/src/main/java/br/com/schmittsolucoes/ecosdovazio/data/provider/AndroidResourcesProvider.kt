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

const val WARRIOR_CLASS_PROFILE_IMAGE_KEY = "classe_guerreiro_perfil"
const val MAGE_CLASS_PROFILE_IMAGE_KEY = "classe_mago_perfil"
const val ARCHER_CLASS_PROFILE_IMAGE_KEY = "classe_arqueiro_perfil"

class AndroidResourcesProvider @Inject constructor() : ResourcesProvider {
    override fun getClassImage(name: String): Int? {
        return when (name) {
            ARCHER_CLASS_IMAGE_KEY -> R.drawable.classe_arqueiro
            WARRIOR_CLASS_IMAGE_KEY -> R.drawable.classe_guerreiro
            MAGE_CLASS_IMAGE_KEY -> R.drawable.classe_mago
            else -> null
        }
    }

    override fun getBattleClassImage(name: String): Int? {
        return when (name) {
            BATTLE_IMAGE_ARCHER_CLASS_IMAGE_KEY -> R.drawable.classe_arqueiro_16_9
            BATTLE_IMAGE_WARRIOR_CLASS_IMAGE_KEY -> R.drawable.classe_guerreiro_16_9
            BATTLE_IMAGE_MAGE_CLASS_IMAGE_KEY -> R.drawable.classe_mago_16_9
            else -> null
        }
    }

    override fun getProfileClassImage(name: String): Int? {
        return when (name) {
            ARCHER_CLASS_PROFILE_IMAGE_KEY -> R.drawable.classe_arqueiro_perfil
            WARRIOR_CLASS_PROFILE_IMAGE_KEY -> R.drawable.classe_guerreiro_perfil
            MAGE_CLASS_PROFILE_IMAGE_KEY -> R.drawable.classe_mago_perfil
            else -> null
        }
    }
}
