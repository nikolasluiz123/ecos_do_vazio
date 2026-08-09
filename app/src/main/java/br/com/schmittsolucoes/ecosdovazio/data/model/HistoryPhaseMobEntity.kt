package br.com.schmittsolucoes.ecosdovazio.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "history_phase_mobs",
    foreignKeys = [
        ForeignKey(
            entity = MobEntity::class,
            parentColumns = ["id"],
            childColumns = ["mob_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = HistoryPhaseEntity::class,
            parentColumns = ["id"],
            childColumns = ["history_phase_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HistoryPhaseMobEntity(
    @PrimaryKey
    override val id: String,

    @ColumnInfo("mob_id", index = true)
    val mobId: String,

    @ColumnInfo("history_phase_id", index = true)
    val historyPhaseId: String,
): UniqueEntity
