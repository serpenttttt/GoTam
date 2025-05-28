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
import com.example.gotam_project.ui.screens.FriendsScreen
import com.example.gotam_project.ui.screens.LoginScreen
import com.example.gotam_project.ui.screens.MainScreen
import com.example.gotam_project.ui.screens.ProfileScreen
import com.example.gotam_project.ui.screens.RegisterScreen
import com.example.gotam_project.ui.screens.WalkArchiveScreen
import com.example.gotam_project.ui.screens.WalkScreen


@Composable
fun NavGraph() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { NavBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "LoginScreen", // Стартуем с экрана входа
            modifier = Modifier.padding(innerPadding)
        ) {
            // Главный экран (только для авторизованных)
            composable("main") {
                MainScreen(navController = navController)
            }

            composable("profile") {
                ProfileScreen(navController = navController)
            }

            // Экран входа
            composable("LoginScreen") {
                LoginScreen(
                    navController = navController,
                    onSuccess = {
                        // При успешном входе переходим на главный экран с очисткой стека
                        navController.navigate("main") {
                            popUpTo("LoginScreen") { inclusive = true }
                        }
                    }
                )
            }

            // Регистрация
            composable("RegisterScreen") {
                RegisterScreen(
                    navController = navController,
                    onSuccess = {
                        // После регистрации переходим на главный экран
                        navController.navigate("main") {
                            popUpTo("LoginScreen") { inclusive = true }
                        }
                    }
                )
            }

            composable("walkArchive") {
                WalkArchiveScreen(navController = navController)
            }

            composable("friends") {
                FriendsScreen()
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