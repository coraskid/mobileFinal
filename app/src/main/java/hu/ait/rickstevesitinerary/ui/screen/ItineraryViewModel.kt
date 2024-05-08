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

//    suspend fun getAllTodoNum(): Int {
//        return todoDAO.getTodosNum()
//    }
//
//    suspend fun getImportantTodoNum(): Int {
//        return todoDAO.getImportantTodosNum()
//    }

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

//    fun changeTodoState(todoItem: TodoItem, value: Boolean) {
//        val changedTodo = todoItem.copy()
//        changedTodo.isDone = value
//        viewModelScope.launch {
//            todoDAO.update(changedTodo)
//        }
//    }

    fun clearAllItineraries() {
        viewModelScope.launch {
            itineraryDAO.deleteAllItineraries()
        }
    }

}