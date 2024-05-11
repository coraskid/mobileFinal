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
            MainScreen { place, start, end, comments, itin ->
                navController.navigate(MainNavigation.DetailScreen.createRoute(place, start, end, comments, itin))
            }
        }
        composable(MainNavigation.DetailScreen.route,
            // extract all and important arguments
            arguments = listOf(
                navArgument("place"){type = NavType.StringType},
                navArgument("start"){type = NavType.StringType},
                navArgument("end"){type = NavType.StringType},
                navArgument("comments"){type = NavType.StringType},
                navArgument("itin"){type = NavType.StringType})
        ) {
            val place = it.arguments?.getString("place")
            val start = it.arguments?.getString("start")
            val end = it.arguments?.getString("end")
            val comments = it.arguments?.getString("comments")?: ""
            val itin = it.arguments?.getString("itin")
            if (place != null && itin != null && start != null && end != null) {
                DetailScreen(
                    place = place,
                    start = start,
                    end = end,
                    comments = comments,
                    itin = itin
                )
            }
        }
    }
}