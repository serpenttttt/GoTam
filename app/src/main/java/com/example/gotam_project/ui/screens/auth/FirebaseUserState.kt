package com.example.gotam_project.ui.screens.auth

sealed class FirebaseUserState {
    object Loading : FirebaseUserState()
    data class Authenticated(val email: String) : FirebaseUserState()
    object Unauthenticated : FirebaseUserState()
    data class Error(val message: String) : FirebaseUserState()
}