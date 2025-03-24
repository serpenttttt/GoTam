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
    val infiniteTransition = rememberInfiniteTransition(label = "TailAnimation")

    val tailRotation by infiniteTransition.animateFloat(
        initialValue = -20f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "TailRotation"
    )

    Box(contentAlignment = Alignment.BottomEnd) {

        // Хвост с анимацией
        Image(
            painter = painterResource(id = R.drawable.dog_tail),
            contentDescription = "Dog Tail",
            modifier = Modifier
                .size(200.dp)
                .offset(x = 5.dp, y = -70.dp)
                .rotate(tailRotation)
        )
        // Тело собаки
        Image(
            painter = painterResource(id = R.drawable.dog_body),
            contentDescription = "Dog Body",
            modifier = Modifier
                .size(300.dp)
                .offset(x = 5.dp, y = -60.dp)
        )


    }
}