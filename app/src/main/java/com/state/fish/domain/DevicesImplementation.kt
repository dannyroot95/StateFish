package com.state.fish.domain

import com.state.fish.data.model.Device
import com.state.fish.data.network.repository.DevicesDataSources
import com.state.fish.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow

class DevicesImplementation (private val dataSource : DevicesDataSources) : DevicesRepository {
    @ExperimentalCoroutinesApi
    override suspend fun getDataDevices(): Flow<Resource<List<Device>>> = dataSource.getDataDevices()
}