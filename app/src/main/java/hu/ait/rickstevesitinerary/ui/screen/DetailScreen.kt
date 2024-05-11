package hu.ait.rickstevesitinerary.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hu.ait.rickstevesitinerary.R

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    place: String,
    start: String,
    end: String,
    comments: String,
    itin: String
) {
    Column {
        Text(text = String.format("%S %S", stringResource(R.string.destination), place))
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = String.format("%S %S", stringResource(R.string.arriving), start))
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = String.format("%S %S", stringResource(R.string.departing), end))
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = String.format("%S %S",
            stringResource(R.string.additional_comments_requested), comments))
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = stringResource(R.string.itinerary))

        Text(
            text = itin,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        )
    }
}

