package com.state.fish.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.state.fish.R
import com.state.fish.data.model.User
import com.state.fish.databinding.ActivityLoginBinding
import com.state.fish.databinding.ActivityRegisterBinding
import com.state.fish.ui.viewModel.LoginAndRegisterViewModel

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var credential : LoginAndRegisterViewModel
    private lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        credential = ViewModelProviders.of(this).get(LoginAndRegisterViewModel::class.java)

        binding.btnRegister.setOnClickListener{
            val email: String = binding.etEmail.text.toString()
            val password: String = binding.etPassword.text.toString()
            user = User(email,password)
            if (email.isNotEmpty() && password.isNotEmpty()) {
                credential.register(user)
            } else {
                Toast.makeText(this, "Debe ingresar la dirección de correo electrónico y la contraseña", Toast.LENGTH_SHORT).show();
            }
        }

    }
}