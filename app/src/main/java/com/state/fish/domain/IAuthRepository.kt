package com.state.fish.domain

import com.google.firebase.auth.FirebaseUser
import com.state.fish.data.model.User

interface IAuthRepository {
    suspend fun signIn(email:String,password:String): FirebaseUser?
    suspend fun signUp(user : User): FirebaseUser?
}