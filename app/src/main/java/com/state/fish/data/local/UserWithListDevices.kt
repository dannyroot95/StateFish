package com.state.fish.data.local

import androidx.room.Embedded
import androidx.room.Relation
import com.state.fish.data.model.ListDevices
import com.state.fish.data.model.User

data class UserWithListDevices (@Embedded val user: User,
                                @Relation(
                                    parentColumn = "id",
                                    entityColumn = "id"
                                )val list: List<ListDevices>)