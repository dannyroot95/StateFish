package com.state.fish.onBoarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.state.fish.ui.view.LoginActivity
import com.state.fish.R
import com.state.fish.databinding.ActivityMainBinding
import com.state.fish.ui.view.DashboardActivity
import com.state.fish.utils.Constants

class MainActivity : AppCompatActivity() {

    lateinit var viewPager : ViewPager
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewPager = ViewPager(supportFragmentManager, 1)
        binding.pager.adapter = viewPager

    }

    override fun onStart() {
        super.onStart()
        val sharedPref = getSharedPreferences(Constants.ON_BOARDING, MODE_PRIVATE)
        val success = sharedPref.getString(Constants.KEY_ON_BOARDING, "")
        val mAuth = FirebaseAuth.getInstance().currentUser

        if (success == Constants.SUCCESS_ON_BOARDING && mAuth == null){
            val intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
        }
        else if (success == Constants.SUCCESS_ON_BOARDING && mAuth != null){
            val intent = Intent(this@MainActivity, DashboardActivity::class.java)
            startActivity(intent)
        }


    }

}