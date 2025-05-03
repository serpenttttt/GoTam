package com.example.gotam_project.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gotam_project.R

@Composable
fun AnimatedDog() {
    // бесконечная анимация
    val infiniteTransition = rememberInfiniteTransition(label = "TailAnimation")

    // анимируем вращение хвоста от -20 до 20 градусов
    val tailRotation by infiniteTransition.animateFloat(
        initialValue = -20f, // начальный угол
        targetValue = 50f, // конечный угол
        animationSpec = infiniteRepeatable( // повторяем анимацию
            animation = tween(durationMillis = 500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse // повторяем анимацию в обратном порядке
        ), label = "TailRotation"
    )

    Box(contentAlignment = Alignment.BottomEnd) {

        // хвост
        Image(
            painter = painterResource(id = R.drawable.dog_tail),
            contentDescription = "Dog Tail",
            modifier = Modifier
                .size(200.dp)
                .offset(x = 5.dp, y = -40.dp)
                .rotate(tailRotation)
        )

        // тело собаки
        Image(
            painter = painterResource(id = R.drawable.dog_body),
            contentDescription = "Dog Body",
            modifier = Modifier
                .size(300.dp)
                .offset(x = 5.dp, y = -30.dp)
        )
    }
}