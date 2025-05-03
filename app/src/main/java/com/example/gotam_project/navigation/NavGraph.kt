import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gotam_project.ui.components.NavBar
import com.example.gotam_project.ui.screens.MainScreen
import com.example.gotam_project.ui.screens.ProfileScreen
import com.example.gotam_project.ui.screens.WalkScreen


@Composable
fun NavGraph() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { NavBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "main",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("main") {
                MainScreen(navController = navController)
            }
            composable("profile") {
                ProfileScreen(navController = navController)
            }

            composable(
                route = "walk/{endTime}",
                arguments = listOf(navArgument("endTime") { type = NavType.LongType })
            ) { backStackEntry ->
                val endTime = backStackEntry.arguments?.getLong("endTime") ?: 0L
                WalkScreen(endTime = endTime, navController = navController)
            }
        }
    }
}