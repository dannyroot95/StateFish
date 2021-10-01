package com.state.fish.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "device")
data class Device(@PrimaryKey val id : String = "",
                  @ColumnInfo val device_name : String = "",
                  @ColumnInfo val temperature : String = "",
                  @ColumnInfo val ph : String = "",
                  @ColumnInfo val turbidity : String = "",
                  @ColumnInfo val oxygen_dissolved : String = "",
                  @ColumnInfo val latitude : Double = 0.0,
                  @ColumnInfo val longitude : Double = 0.0)
