package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.classes

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.ClassEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.ClassSelectionTuple

import kotlinx.coroutines.flow.Flow

@Dao
interface ClassRoomDAO: ClassLocalDataSource, RoomLocalDataSource<ClassEntity> {

    @Query("select exists(select 1 from classes)")
    override suspend fun getExistsClass(): Boolean

    @Query("""
        select classes.id as id,
               coalesce(class_name.translated_text, class_name_default.translated_text) as name,
               coalesce(class_description.translated_text, class_description_default.translated_text) as description,
               classes.presentation_image_name as presentationImageName
        from classes
        left join translations class_name on class_name.id = classes.name_translation_id and class_name.language_id = :languageTag
        left join translations class_name_default on class_name_default.id = classes.name_translation_id and class_name_default.language_id = (select id from languages where is_default = 1 limit 1)
        left join translations class_description on class_description.id = classes.description_translation_id and class_description.language_id = :languageTag
        left join translations class_description_default on class_description_default.id = classes.description_translation_id and class_description_default.language_id = (select id from languages where is_default = 1 limit 1)
    """)
    override fun getClassesForSelection(languageTag: String): Flow<List<ClassSelectionTuple>>
}
