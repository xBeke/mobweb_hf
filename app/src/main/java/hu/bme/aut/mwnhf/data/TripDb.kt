package hu.bme.aut.mwnhf.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [Trip::class], version = 1)
@TypeConverters(CoordTypeConverter::class)
abstract class TripDb: RoomDatabase()  {
    abstract fun TripDao(): TripDao

    companion object {
        fun getDatabase(applicationContext: Context): TripDb {
            return Room.databaseBuilder(
                applicationContext,
                TripDb::class.java,
                "trips"
            ).build()
        }
    }
}
