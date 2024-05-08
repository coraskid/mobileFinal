package hu.ait.rickstevesitinerary.ui.navigation

import hu.ait.rickstevesitinerary.data.Itinerary

sealed class MainNavigation(val route: String) {

    object MainScreen: MainNavigation("mainscreen")
    object DetailScreen : MainNavigation("detailscreen?itin={itin}&place={place}") {
        fun createRoute(itin: String, place: String): String {
            return "summaryscreen?itin=$itin&place=$place"
        }
    }
}