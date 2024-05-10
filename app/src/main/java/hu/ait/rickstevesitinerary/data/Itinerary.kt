package hu.ait.rickstevesitinerary.data

import androidx.compose.runtime.MutableIntState
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "itintable")
data class Itinerary(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "Title") val title:String,
    @ColumnInfo(name = "Place") val place:String,
    @ColumnInfo(name = "Start Date") val startDate: String, //change to some date thing
    @ColumnInfo(name = "End Date") val endDate: String,
    @ColumnInfo(name = "Comments") val comment:String,
    @ColumnInfo(name = "Details") val details:String //this is ChatGPT thing
)

data class Date(
    val year: Int?,
    val month: Int?,
    val day: Int?
)