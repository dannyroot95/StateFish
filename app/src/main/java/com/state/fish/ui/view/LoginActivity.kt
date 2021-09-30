package com.state.fish.ui.view
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.state.fish.data.network.repository.AuthDataSource
import com.state.fish.data.network.repository.RepositoryAuth
import com.state.fish.databinding.ActivityLoginBinding
import com.state.fish.ui.base.BaseActivity
import com.state.fish.ui.viewModel.ViewModelAuth
import com.state.fish.vo.Resource

class LoginActivity : BaseActivity() {

    private lateinit var binding : ActivityLoginBinding

    private val viewModel by viewModels<ViewModelAuth> {
        ViewModelAuth.AuthViewModelFactory(
            RepositoryAuth(
                AuthDataSource()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        binding.btnLogin.setOnClickListener{
            observeLogin()
        }
    }

    private fun observeLogin() {
        val email: String = binding.etEmail.text.toString()
        val password: String = binding.etPassword.text.toString()

        if (email.isNotEmpty() && password.isNotEmpty()) {
            viewModel.signIn(email,password).observe(this, { result ->
                when (result) {
                    is Resource.Loading -> {
                        showDialog("Iniciando sesion...")
                    }
                    is Resource.Success -> {
                        hideDialog()
                        val intent = Intent(this, DashboardActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        startActivity(intent)
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                    }
                    is Resource.Failure -> {
                        hideDialog()
                        /*Toast.makeText(
                            this,
                            "Ocurrio un error ${result.exception}",
                            Toast.LENGTH_SHORT
                        ).show()*/
                        Toast.makeText(
                            this,
                            "Ocurrio un error, Verifique sus datos!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            })
        }
        else{
            Toast.makeText(
                this,
                "Complete los campos",
                Toast.LENGTH_SHORT).show()
        }
    }

    override fun onBackPressed() {
    }

}