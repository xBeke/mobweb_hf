package hu.bme.aut.mwnhf.data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.*

@Entity(tableName = "trips")
class Trip (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "startTime") var startTime: Long,
    @ColumnInfo(name = "endTime") var endTime: Long) {
    fun getLengthTime(): String {
        val elapsedTimeFormat = SimpleDateFormat("HH:mm:ss")
        elapsedTimeFormat.timeZone = (TimeZone.getTimeZone("GMT"))
        return elapsedTimeFormat.format(Date(endTime - startTime))
    }
    fun getLengthInM():String {
        return "400m"
    }
    fun getAverageSpeed():String {
        return "20km/h"
    }
    fun getMaxSpeed(): String {
        return "15km/h"
    }
    fun getDownhill():String {
        return "28m"
    }
    fun getUphill():String {
        return "69m"
    }
    fun getStart(): String {
        val startEndFormat = SimpleDateFormat("HH:mm")
        startEndFormat.timeZone = (TimeZone.getTimeZone("GMT+1"))
        return startEndFormat.format(Date(startTime))
    }
    fun getEnd(): String {
        val startEndFormat = SimpleDateFormat("HH:mm")
        startEndFormat.timeZone = (TimeZone.getTimeZone("GMT+1"))
        return startEndFormat.format(Date(endTime))
    }
}
