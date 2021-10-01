package com.state.fish.data.network.repository

import android.content.Context
import androidx.room.Room
import com.google.firebase.database.*
import com.state.fish.data.local.AppUserDatabase
import com.state.fish.data.model.Device
import com.state.fish.utils.Constants
import com.state.fish.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@Suppress("UNREACHABLE_CODE")
class DevicesDataSources(val context: Context) {

    @ExperimentalCoroutinesApi
    suspend fun getDataDevices(): Flow<Resource<List<Device>>> = callbackFlow {

        val devicesList = mutableListOf<Device>()
        var reference : Query? = null

        val db = Room.databaseBuilder(
            context,
            AppUserDatabase::class.java, Constants.DATABASE_USER
        ).build()

        val devices = db.userDao().getDevices()

        try {
            reference = FirebaseDatabase.getInstance().reference.child("Devices")

        }catch (e: Exception){
            close(e)
        }

        val suscription = reference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    devicesList.clear()
                    for (childSnapshot in snapshot.children) {
                        val data = childSnapshot.getValue(Device::class.java)
                        for (i in devices.indices) {
                            if (data!!.id == devices[i].id) {
                                devicesList.add(data)
                            }
                        }
                    }
                }
                offer(Resource.Success(devicesList))
                //db.userDao().insertDataDevice(devicesList)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        awaitClose { reference?.removeEventListener(suscription!!)}
    }

}