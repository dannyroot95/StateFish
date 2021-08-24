package com.state.fish.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseUser
import com.state.fish.data.network.AppRepository


class LoggedInViewModel(application: Application) : AndroidViewModel(application) {

    private val authAppRepository: AppRepository = AppRepository(application)
    val userLiveData: MutableLiveData<FirebaseUser> = authAppRepository.getUserLiveData()
    val loggedOutLiveData: MutableLiveData<Boolean> = authAppRepository.getLoggedOutLiveData()

    fun logOut() {
        authAppRepository.logOut()
    }

}