package br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.chars

import androidx.room.Dao
import androidx.room.Query
import br.com.schmittsolucoes.ecosdovazio.data.datasource.local.database.access.RoomLocalDataSource
import br.com.schmittsolucoes.ecosdovazio.data.model.CharEntity
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.BattleCharTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharAttributesTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharBaseDamageDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharCriticalDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharDodgeDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharHeaderTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharHealthDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharLevelInfoTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharMagicResistanceDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharPhysicalResistanceDataTuple
import br.com.schmittsolucoes.ecosdovazio.data.model.tuples.CharSelectionTuple
import kotlinx.coroutines.flow.Flow

@Dao
interface CharRoomDAO: CharLocalDataSource, RoomLocalDataSource<CharEntity> {

    @Query("select exists(select * from chars where name = :name)")
    override suspend fun getExistsByName(name: String): Boolean

    @Query("""
        select chars.id as id, 
               chars.name as name, 
               classes.presentation_image_name as presentationImageName 
         from chars
         inner join classes on classes.id = chars.class_id
         where chars.user_id = :userId
    """)
    override fun getUserChars(userId: String): Flow<List<CharSelectionTuple>>

    @Query("""
        select chars.name as name, 
               classes.profile_image_name as profileImageName
        from chars
        inner join classes on classes.id = chars.class_id
        where chars.id = :charId
    """)
    override fun getCharHeader(charId: String): Flow<CharHeaderTuple?>

    @Query("""
        select classes.class_category as classCategory, 
               chars.vitality as charVitality,
               classes.increment_vitality as classIncrementVitality,
               specializations.increment_vitality as specializationIncrementVitality
        from chars
        inner join classes on classes.id = chars.class_id
        left join specializations on specializations.id = chars.specialization_id
        where chars.id = :charId
    """)
    override fun getCharHealthDataTuple(charId: String): Flow<CharHealthDataTuple>

    @Query("""
        select classes.class_category as classCategory,
               chars.strength as charStrength,
               classes.increment_strength as classIncrementStrength,
               specializations.increment_strength as specializationIncrementStrength,
               chars.dexterity as charDexterity,
               classes.increment_dexterity as classIncrementDexterity,
               specializations.increment_dexterity as specializationIncrementDexterity,
               chars.intelligence as charIntelligence,
               classes.increment_intelligence as classIncrementIntelligence,
               specializations.increment_intelligence as specializationIncrementIntelligence
        from chars
        inner join classes on classes.id = chars.class_id
        left join specializations on specializations.id = chars.specialization_id
        where chars.id = :charId
    """)
    override fun getCharBaseDamageDataTuple(charId: String): Flow<CharBaseDamageDataTuple>

    @Query("""
        select classes.class_category as classCategory,
               chars.physical_resistance as charPhysicalResistance,
               classes.increment_physical_resistance as classIncrementPhysicalResistance,
               specializations.increment_physical_resistance as specializationIncrementPhysicalResistance
        from chars
        inner join classes on classes.id = chars.class_id
        left join specializations on specializations.id = chars.specialization_id
        where chars.id = :charId
    """)
    override fun getCharPhysicalResistanceDataTuple(charId: String): Flow<CharPhysicalResistanceDataTuple>

    @Query("""
        select classes.class_category as classCategory,
               chars.magic_resistance as charMagicResistance,
               classes.increment_magic_resistance as classIncrementMagicResistance,
               specializations.increment_magic_resistance as specializationIncrementMagicResistance
        from chars
        inner join classes on classes.id = chars.class_id
        left join specializations on specializations.id = chars.specialization_id
        where chars.id = :charId
    """)
    override fun getCharMagicResistanceDataTuple(charId: String): Flow<CharMagicResistanceDataTuple>

    @Query("""
        select classes.class_category as classCategory,
               chars.dexterity as charDexterity,
               classes.increment_dexterity as classIncrementDexterity,
               specializations.increment_dexterity as specializationIncrementDexterity
        from chars
        inner join classes on classes.id = chars.class_id
        left join specializations on specializations.id = chars.specialization_id
        where chars.id = :charId
    """)
    override fun getCharCriticalDataTuple(charId: String): Flow<CharCriticalDataTuple>

    @Query("""
        select classes.class_category as classCategory,
               chars.agility as charAgility,
               classes.increment_agility as classIncrementAgility,
               specializations.increment_agility as specializationIncrementAgility
        from chars
        inner join classes on classes.id = chars.class_id
        left join specializations on specializations.id = chars.specialization_id
        where chars.id = :charId
    """)
    override fun getCharDodgeDataTuple(charId: String): Flow<CharDodgeDataTuple>

    @Query("""
        select chars.strength as charStrength,
               classes.increment_strength as classIncrementStrength,
               specializations.increment_strength as specializationIncrementStrength,
               
               chars.dexterity as charDexterity,
               classes.increment_dexterity as classIncrementDexterity,
               specializations.increment_dexterity as specializationIncrementDexterity,
               
               chars.intelligence as charIntelligence,
               classes.increment_intelligence as classIncrementIntelligence,
               specializations.increment_intelligence as specializationIncrementIntelligence,
               
               chars.physical_resistance as charPhysicalResistance,
               classes.increment_physical_resistance as classIncrementPhysicalResistance,
               specializations.increment_physical_resistance as specializationIncrementPhysicalResistance,
               
               chars.magic_resistance as charMagicResistance,
               classes.increment_magic_resistance as classIncrementMagicResistance,
               specializations.increment_magic_resistance as specializationIncrementMagicResistance,
               
               chars.vitality as charVitality,
               classes.increment_vitality as classIncrementVitality,
               specializations.increment_vitality as specializationIncrementVitality,
               
               chars.agility as charAgility,
               classes.increment_agility as classIncrementAgility,
               specializations.increment_agility as specializationIncrementAgility
        from chars
        inner join classes on classes.id = chars.class_id
        left join specializations on specializations.id = chars.specialization_id
        where chars.id = :charId
    """)
    override fun getCharAttributesDataTuple(charId: String): Flow<CharAttributesTuple>

    @Query("""
        select chars.level as level, 
               chars.experience as experience 
        from chars 
        where chars.id = :charId
    """)
    override fun getCharLevelInfoDataTuple(charId: String): Flow<CharLevelInfoTuple>

    @Query("select * from chars where id = :id")
    override suspend fun getById(id: String): CharEntity

    @Query("select * from chars where id = :id")
    override fun getByIdObservable(id: String): Flow<CharEntity?>

    @Query("""
        select chars.level as level, 
               chars.name as name,
               coalesce(specializations.battle_image_name, classes.battle_image_name) as battleImageName,
               classes.class_category as classCategory,
               
               chars.strength as charStrength,
               classes.increment_strength as classIncrementStrength,
               specializations.increment_strength as specializationIncrementStrength,
               
               chars.dexterity as charDexterity,
               classes.increment_dexterity as classIncrementDexterity,
               specializations.increment_dexterity as specializationIncrementDexterity,
               
               chars.intelligence as charIntelligence,
               classes.increment_intelligence as classIncrementIntelligence,
               specializations.increment_intelligence as specializationIncrementIntelligence,
               
               chars.physical_resistance as charPhysicalResistance,
               classes.increment_physical_resistance as classIncrementPhysicalResistance,
               specializations.increment_physical_resistance as specializationIncrementPhysicalResistance,
               
               chars.magic_resistance as charMagicResistance,
               classes.increment_magic_resistance as classIncrementMagicResistance,
               specializations.increment_magic_resistance as specializationIncrementMagicResistance,
               
               chars.vitality as charVitality,
               classes.increment_vitality as classIncrementVitality,
               specializations.increment_vitality as specializationIncrementVitality,
               
               chars.agility as charAgility,
               classes.increment_agility as classIncrementAgility,
               specializations.increment_agility as specializationIncrementAgility
        from chars
        inner join classes on classes.id = chars.class_id
        left join specializations on specializations.id = chars.specialization_id
        where chars.id = :charId
    """)
    override fun getBattleChar(charId: String): Flow<BattleCharTuple>
}