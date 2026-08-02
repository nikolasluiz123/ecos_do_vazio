package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.resources

import br.com.schmittsolucoes.ecosdovazio.R
import javax.inject.Inject

const val ARCHER_CLASS_IMAGE_KEY = "classe_arqueiro"
const val WARRIOR_CLASS_IMAGE_KEY = "classe_guerreiro"
private const val MAGE_CLASS_IMAGE_KEY = "classe_mago"

class AndroidResourcesLocalDataSource @Inject constructor(): ResourcesLocalDataSource {
    override fun getClassImage(name: String): Int? {
        return when (name) {
            ARCHER_CLASS_IMAGE_KEY -> R.drawable.classe_arqueiro
            WARRIOR_CLASS_IMAGE_KEY -> R.drawable.classe_guerreiro
            MAGE_CLASS_IMAGE_KEY -> R.drawable.classe_mago
            else -> null
        }
    }

}