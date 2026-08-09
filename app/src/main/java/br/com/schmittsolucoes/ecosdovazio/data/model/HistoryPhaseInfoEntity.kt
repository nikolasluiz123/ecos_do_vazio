package br.com.schmittsolucoes.ecosdovazio.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(
    tableName = "history_phase_info",
    foreignKeys = [
        ForeignKey(
            entity = CharEntity::class,
            parentColumns = ["id"],
            childColumns = ["char_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = HistoryPhaseEntity::class,
            parentColumns = ["id"],
            childColumns = ["phase_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class HistoryPhaseInfoEntity(
    @PrimaryKey
    override val id: String,

    @ColumnInfo("char_id", index = true)
    val charId: String,

    @ColumnInfo("phase_id", index = true)
    val phaseId: String,

    @ColumnInfo("finished_at")
    val finishedAt: Instant? = null,

    @ColumnInfo("try_number")
    val tryNumber: Long,
): UniqueEntity
