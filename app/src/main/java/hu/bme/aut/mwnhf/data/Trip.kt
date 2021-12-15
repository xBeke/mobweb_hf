package hu.bme.aut.mwnhf.data
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

@Entity(tableName = "trips")
class Trip (
    @ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long? = null,
    @ColumnInfo(name = "startTime") var startTime: Long,
    @ColumnInfo(name = "endTime") var endTime: Long,
    @ColumnInfo(name = "cords") var cords: ArrayList<Coord>) {

    fun getLengthTime(): String {
        val elapsedTimeFormat = SimpleDateFormat("HH:mm:ss")
        elapsedTimeFormat.timeZone = (TimeZone.getTimeZone("GMT"))
        return elapsedTimeFormat.format(Date(endTime - startTime))
    }
    fun getLengthInM():String {
        return cords.size.toString()
    }
    fun getAverageSpeed():String {
        return (cords.map { it.speed }.sum() / cords.size).toString() + "km/h"
    }
    fun getMaxSpeed(): String {
        return (cords.map { it.speed }.maxOrNull() ?: 0).toString() + "km/h"
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

class CoordTypeConverter{
    @TypeConverter
    fun fromString(value: String?): ArrayList<Coord>{
        val listType = object :TypeToken<ArrayList<Coord>>(){}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<Coord>): String{
        return Gson().toJson(list)
    }
}
