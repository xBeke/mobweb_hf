package hu.bme.aut.mwnhf.data

import androidx.room.*

@Dao
interface TripDao {
    @Query("SELECT * FROM trips")
    fun getAll(): List<Trip>

    @Query("DELETE FROM trips;")
    fun deleteAll()

    @Query("SELECT * FROM trips WHERE trips.id = :id")
    fun getOne(id: Long): Trip

    @Query("SELECT id FROM trips ORDER BY id DESC LIMIT 1 ")
    fun getLastId(): Long

    @Insert
    fun insert(trip: Trip)

    @Update
    fun update(trip: Trip)

    @Delete
    fun deleteItem(trip: Trip)
}
