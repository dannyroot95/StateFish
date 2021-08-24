package com.state.fish.data.network

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.state.fish.data.model.User
import com.state.fish.ui.view.DashboardActivity


class AppRepository(private val application: Application) {

    private val userMutableLiveData : MutableLiveData<FirebaseUser> = MutableLiveData()
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val loggedOutLiveData: MutableLiveData<Boolean> = MutableLiveData()

    init {
        if (firebaseAuth.currentUser != null){
            userMutableLiveData.postValue(firebaseAuth.currentUser)
            loggedOutLiveData.postValue(false)
        }

    }

    fun loginUser(user : User){
        firebaseAuth.signInWithEmailAndPassword(user.email, user.password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                userMutableLiveData.postValue(firebaseAuth.currentUser)
            }
            else{
                Toast.makeText(application, "Login fallido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun registerUser(user: User){
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password).addOnCompleteListener { task ->
            if (task.isSuccessful){
                userMutableLiveData.postValue(firebaseAuth.currentUser)
            }
            else{
                Toast.makeText(application, "Registro fallido", Toast.LENGTH_SHORT).show()
            }
        }.addOnSuccessListener {
            Toast.makeText(application, "Usuario creado", Toast.LENGTH_SHORT).show()
            val intent = Intent(application,DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            application.startActivity(Intent(intent))
        }
    }

    fun logOut() {
        firebaseAuth.signOut()
        loggedOutLiveData.postValue(true)
    }

    fun getUserLiveData(): MutableLiveData<FirebaseUser> {
        return userMutableLiveData
    }

    fun getLoggedOutLiveData(): MutableLiveData<Boolean> {
        return loggedOutLiveData
    }

}