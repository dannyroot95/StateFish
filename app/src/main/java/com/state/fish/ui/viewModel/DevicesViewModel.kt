package com.state.fish.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.state.fish.domain.DevicesRepository
import com.state.fish.vo.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect

class DevicesViewModel (private val repository : DevicesRepository) : ViewModel() {

    fun fetchDataDevice() = liveData(viewModelScope.coroutineContext + Dispatchers.Main) {
        emit(Resource.Loading())

        kotlin.runCatching {
            repository.getDataDevices()
        }.onSuccess { flowlist ->
            flowlist.collect {
                emit(it)
            }
        }.onFailure { throwable ->
            emit(Resource.Failure(Exception(throwable.message)))
        }

    }
}

class DevicesViewModelFactory(private val repository : DevicesRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(DevicesRepository::class.java).newInstance(repository)
    }
}