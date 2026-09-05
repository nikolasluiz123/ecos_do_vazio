package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.specialization

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.SpecializationEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.SpecializationSelectionTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface SpecializationRoomDAO: SpecializationLocalDataSource, RoomLocalDataSource<SpecializationEntity> {

    @Query("""
        select specializations.id as id,
               coalesce(spec_name.translated_text, spec_name_default.translated_text) as name,
               coalesce(spec_description.translated_text, spec_description_default.translated_text) as description,
               specializations.presentation_image_name as presentationImageName
        from specializations
        inner join chars on chars.class_id = specializations.class_id and chars.id = :charId
        left join translations spec_name on spec_name.id = specializations.name_translation_id and spec_name.language_id = :languageTag
        left join translations spec_name_default on spec_name_default.id = specializations.name_translation_id and spec_name_default.language_id = (select id from languages where is_default = 1 limit 1)
        left join translations spec_description on spec_description.id = specializations.description_translation_id and spec_description.language_id = :languageTag
        left join translations spec_description_default on spec_description_default.id = specializations.description_translation_id and spec_description_default.language_id = (select id from languages where is_default = 1 limit 1)
    """)
    override fun getSpecializationsForSelection(charId: String, languageTag: String): Flow<List<SpecializationSelectionTuple>>
}
