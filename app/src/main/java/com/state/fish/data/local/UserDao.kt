package com.state.fish.data.local

import androidx.room.*
import com.state.fish.data.model.ListDevices
import com.state.fish.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUser(): User

    @Query("SELECT * FROM user WHERE id IN (:id)")
    fun loadUserData(id: String): List<User>

    @Insert
    fun insertData(vararg user: User)

    @Delete
    fun deleteData(user: User)

    @Insert
    fun insertDataListDevice(vararg devices: ListDevices)

    @Transaction
    @Query("SELECT * FROM user")
    fun getUsersWithPlaylists(): List<UserWithListDevices>

    @Query("SELECT * FROM list_devices")
    suspend fun getDevices(): List<ListDevices>

}