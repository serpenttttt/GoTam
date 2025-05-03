package com.example.gotam_project.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gotam_project.R
import com.example.gotam_project.ui.components.AnimatedDog
import com.example.gotam_project.ui.components.NavBar
import com.example.gotam_project.ui.screens.main.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    val pet by viewModel.pet.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.nature_background),
            contentDescription = "Nature Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))

            // Собака
            AnimatedDog()

            Spacer(modifier = Modifier.height(16.dp))

            // Кнопка "Гулять"
            Button(
                onClick = { viewModel.setWalkTime(System.currentTimeMillis()) },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
                modifier = Modifier
                    .width(200.dp)
                    .height(60.dp)
            ) {
                Text(text = "ГУЛЯТЬ", fontSize = 20.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(32.dp))
        }


        // Нижняя панель навигации
       NavBar(modifier = Modifier.align(Alignment.BottomCenter))
    }
}