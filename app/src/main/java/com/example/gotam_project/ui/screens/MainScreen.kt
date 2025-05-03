package com.example.gotam_project.ui.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gotam_project.R
import com.example.gotam_project.ui.components.AnimatedDog
import com.example.gotam_project.ui.screens.main.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val pet by viewModel.pet.collectAsState()

    val sheetState = rememberModalBottomSheetState()
    var showSheet by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundImage()

        Content(
            viewModel = viewModel,
            onWalkClick = { showSheet = true }
        )

        if (showSheet) {
            WalkTimerBottomSheet(
                sheetState = sheetState,
                onDismiss = { showSheet = false },
                onStartWalk = { endTime ->
                    showSheet = false
                    navController.navigate("walk/$endTime")
                }
            )
        }
    }
}

@Composable
private fun BackgroundImage() {
    Image(
        painter = painterResource(id = R.drawable.nature_background),
        contentDescription = "фон приложения",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun Content(
    viewModel: MainViewModel,
    onWalkClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 80.dp)
    ) {
        Spacer(modifier = Modifier.weight(20f))

        AnimatedDog()

        Spacer(modifier = Modifier.height(36.dp))

        WalkButton(onClick = onWalkClick)

        Spacer(modifier = Modifier.height(0.dp))
    }
}

@Composable
private fun WalkButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726)),
        modifier = Modifier
            .width(200.dp)
            .height(60.dp)
    ) {
        Text(text = "ГУЛЯТЬ", fontSize = 32.sp, color = Color.White)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WalkTimerBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onStartWalk: (Long) -> Unit
) {
    var selectedMinutes by remember { mutableStateOf(30) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Text(
                "новая цель",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "$selectedMinutes минут",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Slider(
                value = selectedMinutes.toFloat(),
                onValueChange = { selectedMinutes = it.toInt() },
                valueRange = 1f..120f,
                steps = 119,
                modifier = Modifier.fillMaxWidth()
            )
            Row(
                    modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
            ) {
            Text("1 мин", color = Color.Gray)
            Text("120 мин", color = Color.Gray)
        }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val endTime = System.currentTimeMillis() + selectedMinutes * 60 * 1000L
                    onStartWalk(endTime)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726))
            ) {
                Text("начать", fontSize = 18.sp)
            }
        }

    }
}