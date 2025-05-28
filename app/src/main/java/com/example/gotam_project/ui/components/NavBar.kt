package com.example.gotam_project.ui.components


import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.gotam_project.navigation.Screen
import com.example.gotam_project.R

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        modifier = modifier
            .height(120.dp)
            .windowInsetsPadding(WindowInsets.navigationBars),
        containerColor = Color(0xFFA3D048)
    ) {
        Screen.bottomNavItems.forEach { screen ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = getIconForRoute(screen.route)),
                        contentDescription = screen.route,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    AnimatedContent(
                        targetState = currentRoute == screen.route,
                        label = "nav_label"
                    ) { isSelected ->
                        if (isSelected) {
                            Text(
                                text = getLabelForRoute(screen.route),
                                color = Color.White,
                                fontSize = 9.sp
                            )
                        }
                    }
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.Black.copy(alpha = 0.6f),
                    indicatorColor = Color(0xFF8BB33B)
                ),
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .height(48.dp)
            )
        }
    }
}

private fun getIconForRoute(route: String): Int {
    return when (route) {
        Screen.Main.route -> R.drawable.paw_icon
        Screen.Friends.route -> R.drawable.friends_icon
        Screen.Profile.route -> R.drawable.profile_icon
        else -> R.drawable.paw_icon
    }
}

private fun getLabelForRoute(route: String): String {
    return when (route) {
        Screen.Main.route -> "Питомец"
        Screen.Friends.route -> "Друзья"
        Screen.Profile.route -> "Профиль"
        else -> ""
    }

}