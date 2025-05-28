package com.example.gotam_project.ui.screens.friends

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotam_project.data.repository.FriendsRepository
import com.example.gotam_project.domain.model.UserDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject



@HiltViewModel
class FriendsViewModel @Inject constructor(
    private val friendsRepository: FriendsRepository
) : ViewModel() {

    private val _friends = MutableStateFlow<List<UserDTO>>(emptyList())
    val friends: StateFlow<List<UserDTO>> = _friends

    private val _searchResults = MutableStateFlow<List<UserDTO>>(emptyList())
    val searchResults: StateFlow<List<UserDTO>> = _searchResults

    fun loadFriends() {
        viewModelScope.launch {
            friendsRepository.getFriends().collect {
                _friends.value = it
            }
        }
    }

    fun searchUsers(emailQuery: String) {
        friendsRepository.searchUsersByEmail(emailQuery) {
            _searchResults.value = it
        }
    }

    fun addFriend(friendUid: String) {
        friendsRepository.addFriend(friendUid) { success ->
            if (success) {
                loadFriends()
            }
        }
    }
}