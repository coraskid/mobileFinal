package hu.ait.rickstevesitinerary.ui.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import hu.ait.rickstevesitinerary.data.Itinerary


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
            ItineraryListContent(Modifier.padding(it))
            if (showAddDialog) {
                AddNewTodoDialog()
            }
        }
    )
}

@Composable
fun ItineraryListContent(
    modifier: Modifier
    //view model
    //navigate
) {
    val itineraryList by itineraryViewModel.getAllItineraryList()
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
        if (true) { // change to if list is empty
            Text(text = "No Itineraries")
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(itineraryList) {
                    ItineraryCard(
                        //stuff for itinerary card
                    )
                }
            }
            if (showEditItineraryDialog) {
                AddNewItineraryDialog() {
                    showEditItineraryDialog = false
                }
            }
        }
    }
}

@Composable
fun ItineraryCard(
    itinerary: Itinerary,
    onItineraryCheckChange: (Boolean) -> Unit = {},
    onRemoveItinerary: () -> Unit = {},
    onEditItinerary: (Itinerary) -> Unit = {}
) {
    //var expanded by rememberSaveable { mutableStateOf(false)} //may not need?
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
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
                Text(itinerary.title, modifier = Modifier.fillMaxWidth(.2f))
                Spacer(modifier = Modifier.fillMaxSize(.55f))
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
fun AddNewTodoDialog(
    // view model
    itineraryToEdit: Itinerary? = null,
    onDismissRequest: () -> Unit
) {
    var itineraryPlace by rememberSaveable {
        mutableStateOf(itineraryToEdit?.place ?: "")
    }
    var itineraryStart by rememberSaveable {
        mutableStateOf(itineraryToEdit?.startDate ?: "") //change starting date to some date class
    }
    var itineraryEnd by rememberSaveable {
        mutableStateOf(itineraryToEdit?.endDate ?: "") // also change
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
                //start and end stuff
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
            }
        }
    }

}