package com.state.fish.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.state.fish.data.model.Device
import com.state.fish.databinding.DeviceItemBinding
import com.state.fish.ui.base.BaseViewHolder

class DeviceListAdapter (private val list : List<Device>) : RecyclerView.Adapter<BaseViewHolder<*>>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding = DeviceItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DevicesViewHolder(binding,parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is DevicesViewHolder -> holder.bind(list[position])
        }
    }

    override fun getItemCount(): Int = list.size

    private inner class DevicesViewHolder(val b : DeviceItemBinding ,
                                          val context: Context ) : BaseViewHolder<Device>(b.root) {
        @SuppressLint("SetTextI18n")
        override fun bind(item: Device) {
            b.itemNameDevice.text = "ID : "+item.id
            b.itemTemperature.text = "Temperatura : "+item.temperature+"C°"
            b.itemPh.text = "PH : "+item.ph
            b.itemTurbidity.text = "Turbidez : "+item.turbidity+" ppm"
            b.itemOxygenDissolved.text = "Oxigeno : "+item.oxygen_dissolved
        }
    }
}