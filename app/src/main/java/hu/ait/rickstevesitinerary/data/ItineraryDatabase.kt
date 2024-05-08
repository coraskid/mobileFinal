package hu.ait.rickstevesitinerary.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Itinerary::class], version = 1, exportSchema = false)
abstract class ItineraryDatabase : RoomDatabase() {

    abstract fun itineraryDao(): ItineraryDAO

    companion object {
        @Volatile
        private var Instance: ItineraryDatabase? = null

        fun getDatabase(context: Context): ItineraryDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ItineraryDatabase::class.java,
                    "todo_database.db")
                    // Setting this option in your app's database builder means that Room
                    // permanently deletes all data from the tables in your database when it
                    // attempts to perform a migration with no defined migration path.
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}