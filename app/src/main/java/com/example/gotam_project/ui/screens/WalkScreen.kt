package com.example.gotam_project.ui.screens

import android.Manifest
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.gotam_project.ui.screens.map.MapScreen
import com.example.gotam_project.util.sensors.StepCounterManager
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gotam_project.ui.screens.walk.WalkViewModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WalkScreen(
    endTime: Long,
    navController: NavController
) {
    val context = LocalContext.current

    // Разрешения на местоположение
    val permissions = remember {
        mutableStateListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                add(Manifest.permission.ACTIVITY_RECOGNITION)
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { }

    // Запросить разрешения при первом запуске
    LaunchedEffect(Unit) {
        val notGranted = permissions.filter {
            ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
        }
        if (notGranted.isNotEmpty()) {
            launcher.launch(notGranted.toTypedArray())
        }
    }

    val pagerState = rememberPagerState()

    HorizontalPager(
        count = 2,
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        when (page) {
            0 -> WalkTimerPage(endTime = endTime, onWalkEnd = {
                navController.popBackStack()
            })
            1 -> MapScreen(navController = navController)
        }
    }
}

@Composable
fun WalkTimerPage(
    endTime: Long,
    onWalkEnd: () -> Unit
) {
    val walkViewModel: WalkViewModel = hiltViewModel()
    val startTime = remember { System.currentTimeMillis() }
    var remainingTime by remember { mutableStateOf(endTime - System.currentTimeMillis()) }

    val context = LocalContext.current
    val stepManager = remember { StepCounterManager(context) }

    val steps by stepManager.stepCount.collectAsState()

    LaunchedEffect(Unit) {
        stepManager.start()
        walkViewModel.startTracking()

        while (remainingTime > 0) {
            delay(1000L)
            remainingTime = endTime - System.currentTimeMillis()
            if (remainingTime <= 0) {
                val duration = System.currentTimeMillis() - startTime
                walkViewModel.saveWalk(steps = steps, duration = duration)
                onWalkEnd()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            stepManager.stop()
        }
    }

    val minutes = (remainingTime / 1000 / 60).coerceAtLeast(0)
    val seconds = (remainingTime / 1000 % 60).coerceAtLeast(0)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.9f)),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("идёт прогулка", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(24.dp))
            Text("осталось: ${minutes} мин ${seconds} сек", style = MaterialTheme.typography.displaySmall)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Шагов: $steps", fontSize = 20.sp)
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = {
                    val duration = System.currentTimeMillis() - startTime
                    walkViewModel.saveWalk(steps = steps, duration = duration)
                    onWalkEnd()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFA726))
            ) {
                Text("завершить", fontSize = 18.sp)
            }
        }
    }
}