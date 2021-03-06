package com.state.fish.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import com.state.fish.core.hide
import com.state.fish.core.show
import com.state.fish.data.local.AppUserDatabase
import com.state.fish.data.model.User
import com.state.fish.data.network.repository.DevicesDataSources
import com.state.fish.databinding.ActivityDashboardBinding
import com.state.fish.domain.DevicesImplementation
import com.state.fish.ui.adapter.DeviceListAdapter
import com.state.fish.ui.viewModel.DevicesViewModel
import com.state.fish.ui.viewModel.DevicesViewModelFactory
import com.state.fish.utils.Constants
import com.state.fish.vo.Resource

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDashboardBinding
    private val viewModel by viewModels<DevicesViewModel>{
        DevicesViewModelFactory(
            DevicesImplementation(
                DevicesDataSources(this)
            )
        )
    }

    val Succes = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = Room.databaseBuilder(applicationContext,
            AppUserDatabase::class.java, Constants.DATABASE_USER).allowMainThreadQueries().build()


        val mAuth = FirebaseAuth.getInstance().currentUser!!.uid

        //Toast.makeText(this,"Usuario : "+mAuth,Toast.LENGTH_SHORT).show()

        binding.imvLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        if (!Succes){
            val data : User = db.userDao().getUser()
            //val data2 = db.userDao().getDevices()
            binding.txtName.text = data.fullName
            Picasso.with(this@DashboardActivity).load(data.imageProfile).into(binding.civProfile)
        }
        else{
            val dbReference : DatabaseReference = Firebase.database.reference
            dbReference.child("Users").child(mAuth).addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()){
                        val data = snapshot.getValue(User::class.java)
                        binding.txtName.text = data!!.fullName
                        Picasso.with(this@DashboardActivity).load(data.imageProfile).into(binding.civProfile)
                        db.userDao().insertData(data)
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }


        viewModel.fetchDataDevice().observe(this, Observer {resource ->
            when(resource){
                is Resource.Loading ->{
                    binding.dashProgress.show()
                }
                is Resource.Success -> {
                    binding.dashProgress.hide()
                    if(resource.data.isEmpty()) {
                        Toast.makeText(this,"Vacio",Toast.LENGTH_SHORT).show()
                        return@Observer
                    }else{
                        binding.rvDevices.layoutManager = LinearLayoutManager(this)
                        binding.rvDevices.setHasFixedSize(true)
                        binding.rvDevices.adapter = DeviceListAdapter(resource.data)
                    }
                }
                is Resource.Failure -> {
                    binding.dashProgress.hide()
                    Toast.makeText(
                        this,
                        "Ocurrio un error: ${resource.exception}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })

    }


    override fun onBackPressed() {
    }

}