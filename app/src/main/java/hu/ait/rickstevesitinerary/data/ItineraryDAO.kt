package hu.ait.rickstevesitinerary.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface ItineraryDAO {
    @Query("SELECT * from itintable")
    fun getAllItineraries(): Flow<List<Itinerary>>

    @Query("SELECT * from itintable WHERE id = :id")
    fun getTodo(id: Int): Flow<Itinerary>

//    @Query("SELECT COUNT(*) from todotable")
//    suspend fun getTodosNum(): Int
//
//    @Query("""SELECT COUNT(*) from todotable WHERE priority="HIGH"""")
//    suspend fun getImportantTodosNum(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(itinerary: Itinerary)

    @Update
    suspend fun update(itinerary: Itinerary)

    @Delete
    suspend fun delete(itinerary: Itinerary)

    @Query("DELETE from itintable")
    suspend fun deleteAllItineraries()
}