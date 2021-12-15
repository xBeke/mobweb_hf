package hu.bme.aut.mwnhf.data

import androidx.room.*

@Dao
interface TripDao {
    @Query("SELECT * FROM trips")
    fun getAll(): List<Trip>

    @Query("DELETE FROM trips;")
    fun deleteAll()

    @Insert
    fun insert(shoppingItems: Trip): Long

    @Update
    fun update(shoppingItem: Trip)

    @Delete
    fun deleteItem(shoppingItem: Trip)
}
