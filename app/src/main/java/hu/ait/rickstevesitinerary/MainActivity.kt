package hu.ait.rickstevesitinerary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import hu.ait.rickstevesitinerary.ui.navigation.MainNavigation
import hu.ait.rickstevesitinerary.ui.theme.RickStevesItineraryTheme
import hu.ait.rickstevesitinerary.ui.screen.MainScreen
import hu.ait.rickstevesitinerary.ui.screen.DetailScreen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RickStevesItineraryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ItinAppNavHost()
                }
            }
        }
    }
}

@Composable
fun ItinAppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = MainNavigation.MainScreen.route
) {
    NavHost(
        modifier = modifier, navController = navController, startDestination = startDestination
    ) {
        composable(MainNavigation.MainScreen.route) {
            MainScreen { itin, place ->
                navController.navigate(MainNavigation.DetailScreen.createRoute(itin, place))
            }
        }
        composable(MainNavigation.DetailScreen.route,
            // extract all and important arguments
            arguments = listOf(
                navArgument("itin"){type = NavType.StringType},
                navArgument("place"){type = NavType.StringType})
        ) {
            val itin = it.arguments?.getString("itin")
            val place = it.arguments?.getString("place")
            if (place != null && itin != null) {
                DetailScreen(
                    itin = itin,
                    place = place
                )
            }
        }
    }
}