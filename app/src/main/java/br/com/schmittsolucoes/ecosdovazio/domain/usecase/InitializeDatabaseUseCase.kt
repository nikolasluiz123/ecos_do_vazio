package br.com.schmittsolucoes.ecosdovazio.domain.usecase

import android.content.Context
import android.content.res.Configuration
import br.com.schmittsolucoes.ecosdovazio.R
import br.com.schmittsolucoes.ecosdovazio.core.database.transaction.DatabaseTransaction
import br.com.schmittsolucoes.ecosdovazio.data.provider.ARCHER_CLASS_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_ARCHER_CLASS_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_MAGE_CLASS_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.BATTLE_IMAGE_WARRIOR_CLASS_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.MAGE_CLASS_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.data.provider.WARRIOR_CLASS_IMAGE_KEY
import br.com.schmittsolucoes.ecosdovazio.domain.model.User
import br.com.schmittsolucoes.ecosdovazio.domain.model.classes.Class
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.ClassCategory
import br.com.schmittsolucoes.ecosdovazio.domain.model.enumeration.TranslationIdentifier
import br.com.schmittsolucoes.ecosdovazio.domain.model.internacionalization.Language
import br.com.schmittsolucoes.ecosdovazio.domain.model.internacionalization.Translation
import br.com.schmittsolucoes.ecosdovazio.domain.provider.IdentifierProvider
import br.com.schmittsolucoes.ecosdovazio.domain.provider.LanguageProvider
import br.com.schmittsolucoes.ecosdovazio.domain.repository.ClassRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.LanguageRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.TranslationRepository
import br.com.schmittsolucoes.ecosdovazio.domain.repository.UserRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class InitializeDatabaseUseCase(
    @param:ApplicationContext private val context: Context,
    private val languageRepository: LanguageRepository,
    private val translationRepository: TranslationRepository,
    private val userRepository: UserRepository,
    private val classRepository: ClassRepository,
    private val languageProvider: LanguageProvider,
    private val identifierProvider: IdentifierProvider,
    private val transaction: DatabaseTransaction
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        transaction.run {
            val languages = initializeLanguages()
            initializeTranslations(languages)
            initializeUser()
            initializeClasses()
        }
    }

    private suspend fun initializeLanguages(): List<Language> {
        if (languageRepository.getExistsLanguage()) {
            return emptyList()
        }

        val portuguese = Language(
            id = languageProvider.getTag("pt", "BR"),
            isDefault = false
        )

        val english = Language(
            id = languageProvider.getTag("en", "US"),
            isDefault = true
        )

        val languages = listOf(portuguese, english)
        languageRepository.save(languages)

        return languages
    }

    private suspend fun initializeTranslations(languages: List<Language>) {
        if (!translationRepository.getExistsTranslation()) {
            val identifiers = TranslationIdentifier.entries.map { it.name }
            translationRepository.saveIdentifiers(identifiers)

            val translations = mutableListOf<Translation>()

            languages.forEach { language ->
                val locale = Locale.forLanguageTag(language.id)
                val configuration = Configuration(context.resources.configuration).apply {
                    setLocale(locale)
                }
                val localizedContext = context.createConfigurationContext(configuration)

                TranslationIdentifier.entries.forEach { identifier ->
                    val resourceId = getResourceId(identifier)
                    val text = localizedContext.getString(resourceId)
                    val translation = Translation(
                        id = identifier,
                        languageId = language.id,
                        translatedText = text
                    )

                    translations.add(translation)
                }
            }

            translationRepository.save(translations)
        }
    }

    private fun getResourceId(identifier: TranslationIdentifier): Int {
        return when (identifier) {
            TranslationIdentifier.WARRIOR_CLASS_NAME -> R.string.warrior_class_name
            TranslationIdentifier.WARRIOR_CLASS_DESCRIPTION -> R.string.warrior_class_description
            TranslationIdentifier.MAGE_CLASS_NAME -> R.string.mage_class_name
            TranslationIdentifier.MAGE_CLASS_DESCRIPTION -> R.string.mage_class_description
            TranslationIdentifier.ARCHER_CLASS_NAME -> R.string.archer_class_name
            TranslationIdentifier.ARCHER_CLASS_DESCRIPTION -> R.string.archer_class_description
        }
    }

    private suspend fun initializeUser() {
        if (!userRepository.getExistsUser()) {
            val user = User(id = identifierProvider.generate())
            userRepository.insert(user)
        }
    }

    private suspend fun initializeClasses() {
        if (!classRepository.getExistsClass()) {
            val warrior = Class(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.WARRIOR_CLASS_NAME,
                descriptionTranslationId = TranslationIdentifier.WARRIOR_CLASS_DESCRIPTION,
                classCategory = ClassCategory.WARRIOR,
                battleImageName = BATTLE_IMAGE_WARRIOR_CLASS_IMAGE_KEY,
                presentationImageName = WARRIOR_CLASS_IMAGE_KEY
            )

            val mage = Class(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.MAGE_CLASS_NAME,
                descriptionTranslationId = TranslationIdentifier.MAGE_CLASS_DESCRIPTION,
                classCategory = ClassCategory.MAGE,
                battleImageName = BATTLE_IMAGE_MAGE_CLASS_IMAGE_KEY,
                presentationImageName = MAGE_CLASS_IMAGE_KEY
            )

            val archer = Class(
                id = identifierProvider.generate(),
                nameTranslationId = TranslationIdentifier.ARCHER_CLASS_NAME,
                descriptionTranslationId = TranslationIdentifier.ARCHER_CLASS_DESCRIPTION,
                classCategory = ClassCategory.ARCHER,
                battleImageName = BATTLE_IMAGE_ARCHER_CLASS_IMAGE_KEY,
                presentationImageName = ARCHER_CLASS_IMAGE_KEY
            )

            val classes = listOf(warrior, mage, archer)
            classRepository.save(classes)
        }
    }
}