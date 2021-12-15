package hu.bme.aut.mwnhf.data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "trips")
class Trip (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "startTime") var startTime: Long,
    @ColumnInfo(name = "endTime") var endTime: Long) {
    fun getLengthInM():Long {
        return 13
    }
    fun getFastestSection():Long {
        return 50
    }
}
