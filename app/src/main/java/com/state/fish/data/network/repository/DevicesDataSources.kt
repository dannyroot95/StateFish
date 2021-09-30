package com.state.fish.data.network.repository

import android.content.Context
import com.google.firebase.database.*
import com.state.fish.data.model.Device
import com.state.fish.utils.TinyDB
import com.state.fish.vo.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@Suppress("UNREACHABLE_CODE")
class DevicesDataSources (val context: Context) {

    @ExperimentalCoroutinesApi
    suspend fun getDataDevices(): Flow<Resource<List<Device>>> = callbackFlow {

        val devicesList = mutableListOf<Device>()
        var reference : Query? = null
        lateinit var tinyDB : TinyDB

        try {
            tinyDB = TinyDB(context)

            reference = FirebaseDatabase.getInstance().reference.child("Devices")

        }catch (e : Exception){
            close(e)
        }

        val suscription = reference?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val dev = tinyDB.getListString("key")
                if (snapshot.exists()){
                    devicesList.clear()
                    for(childSnapshot in snapshot.children){
                        val data = childSnapshot.getValue(Device::class.java)
                        for (i in dev){
                            if (data!!.id == i){
                                devicesList.add(data)
                            }
                        }
                    }
                }
                offer(Resource.Success(devicesList))
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })

        awaitClose { reference?.removeEventListener(suscription!!)}
    }

}