package com.state.fish.data.network.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.state.fish.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthDataSource {
    suspend fun signIn(email: String, password: String): FirebaseUser? {
        return withContext(Dispatchers.IO) {
            val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
            authResult.user
        }
    }

    suspend fun signUp(user : User): FirebaseUser? {
        return withContext(Dispatchers.IO) {
            val authResult =
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(user.email, user.password).await()
            authResult.user?.uid?.let { uid ->
                val db : DatabaseReference = Firebase.database.reference
                user.id = uid
                user.password = ""
                db.child("Users").child(uid).setValue(user)
            }
            authResult.user
        }
    }

}