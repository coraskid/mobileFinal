package hu.ait.rickstevesitinerary.ui.screen

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete



@Composable
fun MainScreen(
    //itinerary view model
    //naviagate
) {
    //coroutine
    var showAddDialog by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Rick Steve's Itineraries") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                actions = {
                    IconButton(onClick = {
                        showAddDialog = true
                    }) {
                        Icon(Icons.Filled.Add, null)
                    }

                    IconButton(onClick = {
                        //clear all itineraries in view motel
                    }) {
                        Icon(Icons.Filled.Delete,null)
                    }
                }
            )
        },
        content = {
            ItineraryListContent()
            if (showAddDialog) {
                AddNewTodoDialog()
            }
        }
    )
}

@Composable
fun AddNewTodoDialog() {
    TODO("Not yet implemented")
}

@Composable
fun ItineraryListContent() {
    TODO("Not yet implemented")
}