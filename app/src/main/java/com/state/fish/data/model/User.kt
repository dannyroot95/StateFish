package com.state.fish.data.model
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey var id : String = "",
    @ColumnInfo var dni : String = "",
    @ColumnInfo var fullName : String = "",
    @ColumnInfo var imageProfile : String = "",
    @ColumnInfo var phone : String = "",
    @ColumnInfo var email : String = "",
    @ColumnInfo var password : String = "")

@Entity(tableName = "list_devices")
data class ListDevices(@PrimaryKey val id : String = "",
                       @ColumnInfo val number_id : String = "")

