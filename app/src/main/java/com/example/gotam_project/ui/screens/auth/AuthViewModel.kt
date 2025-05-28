package com.example.gotam_project.ui.screens.auth


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gotam_project.domain.model.UserDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _authState = MutableStateFlow<FirebaseUserState>(FirebaseUserState.Loading)
    val authState: StateFlow<FirebaseUserState> = _authState

    val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    init {
        checkCurrentUser()
    }

    fun checkCurrentUser() {
        val user = firebaseAuth.currentUser
        _authState.value = if (user != null) {
            FirebaseUserState.Authenticated(user.email ?: "Без email")
        } else {
            FirebaseUserState.Unauthenticated
        }
    }

    fun login(email: String, password: String) {
        _authState.value = FirebaseUserState.Loading
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _authState.value = FirebaseUserState.Authenticated(it.user?.email ?: "Пользователь")
            }
            .addOnFailureListener {
                _authState.value = FirebaseUserState.Error(it.message ?: "Ошибка входа")
            }
    }

    fun register(email: String, password: String) {
        _authState.value = FirebaseUserState.Loading
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener { result ->
                val user = result.user
                if (user != null) {
                    val userDTO = UserDTO(
                        uid = user.uid,
                        email = user.email ?: "",
                        name = user.email?.substringBefore("@") ?: "Пользователь"
                    )

                    firestore.collection("users")
                        .document(user.uid)
                        .set(userDTO)
                        .addOnSuccessListener {
                            _authState.value = FirebaseUserState.Authenticated(user.email ?: "Пользователь")
                        }
                        .addOnFailureListener { e ->
                            _authState.value = FirebaseUserState.Error("Ошибка при создании пользователя в Firestore: ${e.message}")
                        }
                }
            }
            .addOnFailureListener {
                _authState.value = FirebaseUserState.Error(it.message ?: "Ошибка регистрации")
            }
    }

    fun logout() {
        firebaseAuth.signOut()
        _authState.value = FirebaseUserState.Unauthenticated
    }
}