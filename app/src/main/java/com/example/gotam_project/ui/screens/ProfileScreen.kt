package com.example.gotam_project.ui.screens

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gotam_project.domain.model.WalkDTO
import com.example.gotam_project.ui.screens.walk.WalkViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import com.example.gotam_project.util.toFormattedDate

@Composable
fun ProfileScreen(
    navController: NavController,
    walkViewModel: WalkViewModel = hiltViewModel()
) {
    val walks by walkViewModel.walks.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Архив прогулок", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        if (walks.isEmpty()) {
            Text("Нет сохранённых прогулок")
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(walks) { walk ->
                    WalkItem(walk = walk)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun WalkItem(walk: WalkDTO) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Дата: ${walk.date.toFormattedDate()}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Шагов: ${walk.steps}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Длительность: ${walk.duration / 1000} сек.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}