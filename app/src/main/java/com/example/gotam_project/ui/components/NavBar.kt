package com.example.gotam_project.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.gotam_project.R

@Composable
fun NavBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .background(Color(0xFFA3D048)),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BottomNavItem(R.drawable.paw_icon, "Питомец")
        BottomNavItem(R.drawable.friends_icon, "Друзья")
        BottomNavItem(R.drawable.map_icon, "Карта")
        BottomNavItem(R.drawable.profile_icon, "Профиль")
    }
}

@Composable
fun BottomNavItem(iconRes: Int, contentDescription: String) {
    Image(
        painter = painterResource(id = iconRes),
        contentDescription = contentDescription,
        modifier = Modifier.size(32.dp)
    )
}