package com.state.fish.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.state.fish.data.model.User
import com.state.fish.data.network.AppRepository


class LoginAndRegisterViewModel(application: Application) : AndroidViewModel(application) {

    private val authAppRepository: AppRepository = AppRepository(application)
    private val userLiveData: MutableLiveData<FirebaseUser> = authAppRepository.getUserLiveData()

    fun login(user : User) {
        authAppRepository.loginUser(user)
    }
    fun register(user : User) {
        authAppRepository.registerUser(user)
    }
    fun getUserLiveData(): MutableLiveData<FirebaseUser> {
        return userLiveData
    }

}