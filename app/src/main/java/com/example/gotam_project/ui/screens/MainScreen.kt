package com.example.gotam_project.presentation.main

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.material.OutlinedTextField
import com.example.gotam_project.viewmodel.MainViewModel

@Composable
fun MainScreen(viewModel: MainViewModel = viewModel()) {
    // Получаем состояние питомца из ViewModel
    val pet = viewModel.pet.collectAsState(initial = null).value
    val walkTime = remember { mutableStateOf("0") } // Для ввода нового времени прогулки

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (pet != null) {
            Text("Pet Name: ${pet.name}")
            Text("Last Walk Time: ${pet.lastWalkTime}")
            Text("Walk Time: ${pet.walkTime}")

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = walkTime.value,
                onValueChange = { walkTime.value = it },
                label = { Text("New Walk Time") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                // Обновляем время прогулки
                val newWalkTime = walkTime.value.toLongOrNull()
                if (newWalkTime != null) {
                    viewModel.setWalkTime(newWalkTime)
                } else {
                    Toast.makeText(viewModel.context, "Invalid input", Toast.LENGTH_SHORT).show()
                }
            }) {
                Text("Update Walk Time")
            }
        } else {
            Text("No pet data available")
        }
    }
}