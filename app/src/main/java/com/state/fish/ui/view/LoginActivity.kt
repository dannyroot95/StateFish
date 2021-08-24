package com.state.fish.ui.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.state.fish.data.model.User
import com.state.fish.databinding.ActivityLoginBinding
import com.state.fish.ui.viewModel.LoginAndRegisterViewModel


class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var credential : LoginAndRegisterViewModel
    private lateinit var user : User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        credential = ViewModelProviders.of(this).get(LoginAndRegisterViewModel::class.java)

        binding.tvRegister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.btnLogin.setOnClickListener {

            val email: String = binding.etEmail.text.toString()
            val password: String = binding.etPassword.text.toString()
            user = User(email,password)

            if (email.isNotEmpty() && password.isNotEmpty()) {
                credential.login(user)
            } else {
                Toast.makeText(this, "Debe ingresar la dirección de correo electrónico y la contraseña", Toast.LENGTH_SHORT).show();
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}