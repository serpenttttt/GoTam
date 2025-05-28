package com.example.gotam_project.data.repository

import com.example.gotam_project.domain.model.UserDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FriendsRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) {

    private val usersCollection = firestore.collection("users")

    private fun getCurrentUserId(): String = firebaseAuth.currentUser?.uid.orEmpty()

    fun getFriends(): Flow<List<UserDTO>> = callbackFlow {
        val userId = getCurrentUserId()
        if (userId.isEmpty()) {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val listener = usersCollection.document(userId).addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val friendsIds = snapshot?.get("friends") as? List<String> ?: emptyList()
            if (friendsIds.isEmpty()) {
                trySend(emptyList())
                return@addSnapshotListener
            }

            usersCollection.whereIn(FieldPath.documentId(), friendsIds)
                .get()
                .addOnSuccessListener { querySnapshot ->
                    val friends = querySnapshot.documents.mapNotNull { it.toObject(UserDTO::class.java) }
                    trySend(friends)
                }
                .addOnFailureListener { e -> close(e) }
        }

        awaitClose { listener.remove() }
    }

    fun searchUsersByEmail(emailQuery: String, onResult: (List<UserDTO>) -> Unit) {
        usersCollection
            .whereGreaterThanOrEqualTo("email", emailQuery)
            .whereLessThanOrEqualTo("email", emailQuery + '\uf8ff')
            .get()
            .addOnSuccessListener { querySnapshot ->
                val users = querySnapshot.documents.mapNotNull { it.toObject(UserDTO::class.java) }
                onResult(users)
            }
            .addOnFailureListener {
                onResult(emptyList())
            }
    }

    fun addFriend(friendUid: String, onComplete: (Boolean) -> Unit) {
        val currentUserId = getCurrentUserId()
        if (currentUserId.isEmpty()) {
            onComplete(false)
            return
        }

        val userDoc = usersCollection.document(currentUserId)
        firestore.runTransaction { transaction ->
            val snapshot = transaction.get(userDoc)
            val currentFriends = snapshot.get("friends") as? MutableList<*> ?: mutableListOf<String>()
            val updatedFriends = currentFriends.mapNotNull { it as? String }.toMutableList()

            if (!updatedFriends.contains(friendUid)) {
                updatedFriends.add(friendUid)
                transaction.update(userDoc, "friends", updatedFriends)
            }
        }.addOnSuccessListener {
            onComplete(true)
        }.addOnFailureListener {
            onComplete(false)
        }
    }
}