package com.state.fish.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.state.fish.data.model.Device
import com.state.fish.data.model.ListDevices
import com.state.fish.data.model.User

@Database(entities = [User::class,ListDevices::class], version = 1)
abstract class AppUserDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}