package com.example.gotam_project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gotam_project.ui.screens.friends.FriendsViewModel
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.graphics.Color

@Composable
fun FriendsScreen(
    viewModel: FriendsViewModel = hiltViewModel()
) {
    val friends by viewModel.friends.collectAsState()
    val searchResults by viewModel.searchResults.collectAsState()
    var searchText by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFBCE075))
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Друзья",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                viewModel.searchUsers(it)
            },
            label = { Text("Поиск по email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(12.dp))

        if (searchText.isNotEmpty()) {
            Text("Результаты поиска:", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            LazyColumn {
                items(searchResults) { user ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(user.email, modifier = Modifier.weight(1f))
                        Button(onClick = {
                            viewModel.addFriend(user.uid)
                            searchText = ""
                        }) {
                            Text("Добавить")
                        }
                    }
                }
            }
        } else {
            Text("Ваши друзья:", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(8.dp))

            if (friends.isEmpty()) {
                Text("Список друзей пуст.")
            } else {
                LazyColumn {
                    items(friends) { friend ->
                        Text(friend.email, modifier = Modifier.padding(8.dp))
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadFriends()
    }
}