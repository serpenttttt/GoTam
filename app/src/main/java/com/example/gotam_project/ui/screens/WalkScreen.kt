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
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.airbnb.lottie.compose.*
import com.example.gotam_project.R
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
    val calories = (steps * 0.04).toInt()

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

    val hours = (remainingTime / 1000 / 3600).coerceAtLeast(0)
    val minutes = (remainingTime / 1000 / 60 % 60).coerceAtLeast(0)
    val seconds = (remainingTime / 1000 % 60).coerceAtLeast(0)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        BackgroundImage()

        // Верхняя информация
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, end = 16.dp)
                .align(Alignment.TopEnd)
        ) {
            Text("$steps ШАГОВ", color = Color(0xFFD81B60), fontSize = 32.sp)
            Text("$calories ККАЛ", color = Color(0xFF1976D2), fontSize = 32.sp)
        }

        // Анимированная собака через Lottie
        val composition by rememberLottieComposition(
            LottieCompositionSpec.Asset("dog_walk_animation.json")
        )
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever
        )

        LottieAnimation(
            composition = composition,
            progress = { progress },
            modifier = Modifier
                .size(450.dp)
                .align(Alignment.Center)
                .offset(y = 80.dp)
        )

        // Таймер
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 90.dp)
                .background(Color(0xFFFFA726), shape = RoundedCornerShape(16.dp))
                .padding(horizontal = 32.dp, vertical = 12.dp)
        ) {
            Text(
                text = String.format("%01d:%02d:%02d", hours, minutes, seconds),
                fontSize = 24.sp,
                color = Color.White
            )
        }

        // Кнопка завершения
        IconButton(
            onClick = {
                val duration = System.currentTimeMillis() - startTime
                walkViewModel.saveWalk(steps = steps, duration = duration)
                onWalkEnd()
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 20.dp)
                .size(56.dp)
                .background(Color.Red, shape = CircleShape)
        ) {
            Icon(Icons.Default.Close, contentDescription = "Закончить", tint = Color.White)
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