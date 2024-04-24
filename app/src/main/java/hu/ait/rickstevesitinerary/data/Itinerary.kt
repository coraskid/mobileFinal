package hu.ait.rickstevesitinerary.data

import androidx.compose.runtime.MutableIntState
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class Itinerary(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "Title") val title:String,
    @ColumnInfo(name = "Place") val place:String,
    @ColumnInfo(name = "Start Date") val startDate: Int, //change to some date thing
    @ColumnInfo(name = "End Date") val endDate: Int,
    @ColumnInfo(name = "Comments") val comment:String,
    @ColumnInfo(name = "Details") val details:String //this is ChatGPT thing
)