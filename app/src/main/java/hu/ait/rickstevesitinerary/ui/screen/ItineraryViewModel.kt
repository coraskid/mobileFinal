package hu.ait.rickstevesitinerary.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.ait.rickstevesitinerary.data.Itinerary
import hu.ait.rickstevesitinerary.data.ItineraryDAO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItineraryViewModel @Inject constructor(
    val itineraryDAO: ItineraryDAO
): ViewModel() {
    fun getAllItinerary(): Flow<List<Itinerary>> {
        return itineraryDAO.getAllItineraries()
    }
    fun addItinerary(itinerary: Itinerary) {
        viewModelScope.launch {
            itineraryDAO.insert(itinerary)
        }
    }


    fun removeItinerary(itinerary: Itinerary) {
        viewModelScope.launch {
            itineraryDAO.delete(itinerary)
        }
    }

    fun editItinerary(editItinerary: Itinerary) {
        viewModelScope.launch {
            itineraryDAO.update(editItinerary)
        }
    }
    fun clearAllItineraries() {
        viewModelScope.launch {
            itineraryDAO.deleteAllItineraries()
        }
    }

}