package com.example.gotam_project.ui.screens


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gotam_project.R
import com.example.gotam_project.ui.components.AnimatedDog
import com.example.gotam_project.ui.screens.main.MainViewModel
import kotlin.math.PI
import kotlin.math.atan2


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
fun WalkTimerBottomSheet(
    sheetState: SheetState,
    onDismiss: () -> Unit,
    onStartWalk: (Long) -> Unit
) {
    var selectedMinutes by remember { mutableStateOf(30f) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = Color(0xFFA3D048),
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFA3D048))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Заголовок
            Text(
                text = "НОВАЯ ЦЕЛЬ",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Круглый слайдер
            CircularSlider(
                value = selectedMinutes,
                onValueChange = { selectedMinutes = it },
                valueRange = 1f..120f,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Кнопка "НАЧАТЬ"
            Button(
                onClick = {
                    val endTime = System.currentTimeMillis() + selectedMinutes.toLong() * 60 * 1000L
                    onStartWalk(endTime)
                },
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9131))
            ) {
                Text("НАЧАТЬ", fontSize = 18.sp)
            }
        }
    }
}
@Composable
fun CircularSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    valueRange: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier
) {
    val stroke = 20f
    val angleRange = 360f   // Полный круг
    val startAngle = -90f    // Старт

    Box(
        modifier = modifier
            .size(260.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    val x = change.position.x - size.width / 2
                    val y = change.position.y - size.height / 2
                    val angle = (atan2(y, x) * 180f / PI.toFloat() + 360f) % 360f

                    val normalized = ((angle - startAngle + 360f) % 360f).coerceIn(0f, angleRange)
                    val newValue = (normalized / angleRange) * (valueRange.endInclusive - valueRange.start) + valueRange.start
                    onValueChange(newValue.coerceIn(valueRange))
                }
            },
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.minDimension / 2.2f
            val center = Offset(size.width / 2, size.height / 2)
            val sweepAngle = ((value - valueRange.start) / (valueRange.endInclusive - valueRange.start)) * angleRange

            // Белый круг
            drawCircle(
                color = Color.White,
                radius = radius,
                center = center,
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )

            // Оранжевая дуга
            drawArc(
                color = Color(0xFFFF9131),
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2),
                style = Stroke(width = stroke, cap = StrokeCap.Round)
            )
        }


        Text(
            text = "${value.toInt()} мин",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
    }
}