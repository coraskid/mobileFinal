package hu.ait.rickstevesitinerary.ui.screen

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import hu.ait.rickstevesitinerary.data.Itinerary
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    itineraryViewModel: ItineraryViewModel = hiltViewModel(),
    onNavigateToDetail: (String, String) -> Unit
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
                        itineraryViewModel.clearAllItineraries()
                    }) {
                        Icon(Icons.Filled.Delete,null)
                    }
                }
            )
        },
        content = {
            ItineraryListContent(
                Modifier.padding(it),
                itineraryViewModel,
                onNavigateToDetail
            )
            if (showAddDialog) {
                AddNewItineraryDialog(itineraryViewModel,
                    onDismissRequest = {
                        showAddDialog = false
                    })
            }
        }
    )
}

@Composable
fun ItineraryListContent(
    modifier: Modifier,
    itineraryViewModel: ItineraryViewModel,
    onNavigateToDetail: (String, String) -> Unit
) {
    val itineraryList by itineraryViewModel.getAllItinerary()
        .collectAsState(initial = emptyList())

    var showEditItineraryDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var itineraryToEdit: Itinerary? by rememberSaveable {
        mutableStateOf(null)
    }

    Column(
        modifier = modifier
    ) {
        if (itineraryList.isEmpty()) { // change to if list is empty
            Text(text = "No Itineraries")
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(itineraryList) {
                    Log.i("MyActivity", "test")
                    ItineraryCard(it,
                        onRemoveItinerary = {itineraryViewModel.removeItinerary(it)},
                        onEditItinerary = {
                            itineraryToEdit = it
                            showEditItineraryDialog = true
                        },
                        onNavigateToDetail
                        //stuff for itinerary card
                    )
                }
            }
            if (showEditItineraryDialog) {
                AddNewItineraryDialog(itineraryViewModel, itineraryToEdit) {
                    showEditItineraryDialog = false
                }
            }
        }
    }
}

@Composable
fun ItineraryCard(
    itinerary: Itinerary,
    //onItineraryCheckChange: (Boolean) -> Unit = {},
    onRemoveItinerary: () -> Unit = {},
    onEditItinerary: (Itinerary) -> Unit = {},
    onNavigateToDetail: (String, String) -> Unit
) {
    var coroutineScope = rememberCoroutineScope()
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier
            .padding(5.dp)
            .clickable(onClick = {
                coroutineScope.launch {
                    onNavigateToDetail(itinerary.details, itinerary.place)
                }
            })
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .animateContentSize()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //Image goes here if you want
                Text(itinerary.place, modifier = Modifier.fillMaxWidth(.2f))
                Spacer(modifier = Modifier.fillMaxSize(.75f))
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit",
                    modifier = Modifier.clickable {
                        onEditItinerary(itinerary)
                    },
                    tint = Color.Blue
                )
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",
                    modifier = Modifier.clickable {
                        onRemoveItinerary()
                    },
                    tint = Color.Red
                )
                //expanded/not expanded
            }
        }
    }
}

@Composable
fun AddNewItineraryDialog(
    itineraryViewModel: ItineraryViewModel,
    itineraryToEdit: Itinerary? = null,
    onDismissRequest: () -> Unit
) {
    var itineraryPlace by rememberSaveable {
        mutableStateOf(itineraryToEdit?.place ?: "")
    }
    var itineraryStart by rememberSaveable {
        mutableStateOf(itineraryToEdit?.startDate ?: "Select Start Date") //change starting date to some date class
    }
    var showDateStartDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var itineraryEnd by rememberSaveable {
        mutableStateOf(itineraryToEdit?.endDate ?: "Select End Date") // also change
    }
    var showDateEndDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var itineraryComments by rememberSaveable {
        mutableStateOf(itineraryToEdit?.comment ?: "")
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column (
                Modifier.padding(16.dp)
            ) {
                Text(
                    text = if (itineraryToEdit == null) "Create Itinerary" else "Edit Details",
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize(Alignment.Center),
                    textAlign = TextAlign.Center,
                )
                OutlinedTextField(
                    value = itineraryPlace,
                    onValueChange = { itineraryPlace = it },
                    label = { Text(text = "Enter Destination here:") }
                )
                Box(contentAlignment = Alignment.Center) {
                    Button(onClick = { showDateStartDialog = true }) {
                        Text(text = itineraryStart)
                    }
                }
                Box(contentAlignment = Alignment.Center) {
                    Button(onClick = { showDateEndDialog = true }) {
                        Text(text = itineraryEnd)
                    }
                }
                //comments
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        if (itineraryToEdit == null) {
                            itineraryViewModel.addItinerary(
                                Itinerary(
                                    title = "Something with place and start and end",
                                    place = itineraryPlace,
                                    startDate = itineraryStart,
                                    endDate = itineraryEnd,
                                    comment = itineraryComments,
                                    details = ""
                                )
                            )
                        } else {
                            val editedItinerary = itineraryToEdit.copy(
                                title = "Something with place and start and end",
                                place = itineraryPlace,
                                startDate = itineraryStart,
                                endDate = itineraryEnd,
                                comment = itineraryComments,
                            )
                            itineraryViewModel.editItinerary(editedItinerary)
                        }
                        onDismissRequest()
                    }) {
                        Text(text = "Create Itinerary")
                    }
                    TextButton(onClick = { onDismissRequest() }) {
                        Text(text = "Cancel")
                    }
                }
                if (showDateStartDialog){
                    MyDatePickerDialog(
                        onDateSelected = { itineraryStart = it },
                        onDismiss = { showDateStartDialog = false }
                    )
                }
                if (showDateEndDialog){
                    MyDatePickerDialog(
                        onDateSelected = { itineraryEnd = it },
                        onDismiss = { showDateEndDialog = false }
                    )
                }
            }
            
        }
    }

}


//Code from https://medium.com/@rahulchaurasia3592/material3-datepicker-and-datepickerdialog-in-compose-in-android-54ec28be42c3
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDatePickerDialog(
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
            return utcTimeMillis <= System.currentTimeMillis()
        }
    })

    val selectedDate = datePickerState.selectedDateMillis?.let {
        convertMillisToDate(it)
    } ?: ""

    DatePickerDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(onClick = {
                onDateSelected(selectedDate)
                onDismiss()
            }

            ) {
                Text(text = "OK")
            }
        },
        dismissButton = {
            Button(onClick = {
                onDismiss()
            }) {
                Text(text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DatePickerView() {
//    val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
//        override fun isSelectableDate(utcTimeMillis: Long): Boolean {
//            return utcTimeMillis <= System.currentTimeMillis()
//        }
//    })
//    val selectedDate = datePickerState.selectedDateMillis?.let {
//        convertMillisToDate(it)
//    }
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        DatePicker(
//            state = datePickerState
//        )
//        Spacer(
//            modifier = Modifier.height(
//                32.dp
//            )
//        )
//        Text(
//            text = selectedDate.toString(),
//            color = Color.Red
//        )
//    }
//}

private fun convertMillisToDate(millis: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy")
    return formatter.format(Date(millis))
}