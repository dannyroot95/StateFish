package com.state.fish.data.model

data class Device(val id : String = "" ,
                  val device_name : String = "",
                  val temperature : String = "" ,
                  val ph : String = "",
                  val turbidity : String = "",
                  val oxygen_dissolved : String = "",
                  val latitude : Double = 0.0,
                  val longitude : Double = 0.0)
