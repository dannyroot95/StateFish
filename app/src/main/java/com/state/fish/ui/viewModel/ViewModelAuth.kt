package com.state.fish.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.state.fish.data.model.User
import com.state.fish.domain.IAuthRepository
import com.state.fish.vo.Resource
import kotlinx.coroutines.Dispatchers

class ViewModelAuth (private val auth : IAuthRepository) : ViewModel() {

    fun signIn(email: String, password: String) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(auth.signIn(email, password)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    fun signUp(user : User) = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(auth.signUp(user)))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    class AuthViewModelFactory(private val repo: IAuthRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(IAuthRepository::class.java).newInstance(repo)
        }
    }

}