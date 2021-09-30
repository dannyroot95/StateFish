package com.state.fish.domain

import com.state.fish.data.model.Device
import com.state.fish.vo.Resource
import kotlinx.coroutines.flow.Flow

interface DevicesRepository {
    suspend fun getDataDevices(): Flow<Resource<List<Device>>>
}