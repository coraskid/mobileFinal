package hu.ait.rickstevesitinerary.ui.navigation

import hu.ait.rickstevesitinerary.data.Itinerary

sealed class MainNavigation(val route: String) {

    object MainScreen: MainNavigation("mainscreen")
    object DetailScreen : MainNavigation("detailscreen?place={place}&start={start}&end={end}&comments={comments}&itin={itin}") {
        fun createRoute(place: String, start: String, end: String, comments: String, itin: String): String {
            return "detailscreen?place=$place&start=$start&end=$end&comments=$comments&itin=$itin"
        }
    }
}