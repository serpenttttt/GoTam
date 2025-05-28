package com.example.gotam_project.navigation

sealed class Screen(val route: String) {
    object Main : Screen("main")
    object Friends : Screen("friends")
    object Profile : Screen("profile")

    companion object {
        val bottomNavItems = listOf(Main, Friends, Profile)
    }
}