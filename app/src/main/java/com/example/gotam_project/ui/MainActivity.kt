package com.example.gotam_project.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import com.example.gotam_project.ui.screens.MainScreen
import com.example.gotam_project.ui.theme.GoTamProjectTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GoTamProjectTheme {
                MainScreen()
            }
        }
    }
}