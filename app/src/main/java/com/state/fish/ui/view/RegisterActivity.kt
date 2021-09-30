package com.state.fish.ui.view

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.gms.tasks.OnSuccessListener
import com.google.gson.GsonBuilder
import com.state.fish.data.model.DNI
import com.state.fish.data.model.User
import com.state.fish.data.model.providers.ImageProvider
import com.state.fish.data.network.repository.AuthDataSource
import com.state.fish.data.network.repository.RepositoryAuth
import com.state.fish.databinding.ActivityRegisterBinding
import com.state.fish.domain.InterfaceDNI
import com.state.fish.ui.base.BaseActivity
import com.state.fish.ui.viewModel.ViewModelAuth
import com.state.fish.utils.FileUtil
import com.state.fish.vo.Resource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.*

class RegisterActivity : BaseActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private var user : User = User()

    private val viewModel by viewModels<ViewModelAuth> {
        ViewModelAuth.AuthViewModelFactory(
            RepositoryAuth(
                AuthDataSource()
            )
        )
    }

    var URL = "https://dniruc.apisperu.com/api/v1/dni/"
    var BASE_TOKEN = "?token="
    var TOKEN = BASE_TOKEN + "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6InNhbWFuLmRhbm55OTVAZ21haWwuY29tIn0.J7apbfAgC6PK_L9EJBkJWMdJmHZZxYWVr2HFEp8WqLQ"

    private val GALLERY_REQUEST = 1
    private var mImageFileProfile: File? = null
    private lateinit var mImageProvider: ImageProvider
    var urlImageProfile = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mImageProvider = ImageProvider("user_photos")

        binding.etDni.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s!!.length == 8) {
                    val DNI: String = binding.etDni.text.toString()
                    searchDni(DNI)
                }
            }
        })

        binding.btnEditDni.setOnClickListener {
            binding.etDni.isEnabled = true
            binding.etFullName.setText("")
            binding.etDni.setText("")
            binding.btnEditDni.visibility = View.GONE
            binding.tilFullName.visibility = View.GONE
        }

        binding.btnRegister.setOnClickListener{
            observeRegisterData()
        }

        binding.civOpenGallery.setOnClickListener{
            openGallery()
        }

    }

    private fun searchDni(dni: String) {
        showDialog("Buscando DNI...")
        val gson = GsonBuilder().serializeNulls().create()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        val interfaceDNI: InterfaceDNI = retrofit.create(InterfaceDNI::class.java)
        val call: Call<DNI> = interfaceDNI.getDataDni(dni + TOKEN)
        call.enqueue(object : Callback<DNI> {
            override fun onResponse(call: Call<DNI>, response: Response<DNI>) {

                if (!response.isSuccessful) {
                    Toast.makeText(this@RegisterActivity, "Error !", Toast.LENGTH_SHORT)
                        .show()
                    binding.etDni.setText("")
                    hideDialog()
                } else if (response.body()!!.apellidoPaterno.isNotEmpty()) {
                    var data = ""
                    data = response.body()!!.apellidoPaterno + " " + response.body()!!
                        .apellidoMaterno + " " + response.body()!!.nombres
                    binding.tilFullName.visibility = View.VISIBLE
                    binding.btnEditDni.visibility = View.VISIBLE
                    binding.etDni.isEnabled = false
                    binding.etFullName.setText(data)
                    hideDialog()
                } else {
                    binding.etDni.setText("")
                    hideDialog()
                    Toast.makeText(this@RegisterActivity, "Error de DNI", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onFailure(call: Call<DNI>, t: Throwable) {
                binding.etDni.setText("")
                hideDialog()
                Toast.makeText(
                    this@RegisterActivity,
                    "Error!, inténtelo mas tarde",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        })

    }

    private fun observeRegisterData() {

        val dni : String = binding.etDni.text.toString()
        val fullName : String = binding.etFullName.text.toString()
        val phone : String = binding.etPhone.text.toString()
        val email: String = binding.etEmail.text.toString()
        val password: String = binding.etPassword.text.toString()

        user = User(
            "",
            dni,
            fullName,
            urlImageProfile,
            phone,
            email,
            password
        )

        if (dni.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {

            viewModel.signUp(user).observe(this, Observer { result ->
                when (result) {
                    is Resource.Loading -> {
                        showDialog("Registrando Usuario...")
                    }
                    is Resource.Success -> {
                        hideDialog()
                        Toast.makeText(this, "Usuario creado!", Toast.LENGTH_LONG).show()
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

        } else {
            Toast.makeText(
                this,
                "Debe ingresar la dirección de correo electrónico y la contraseña!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            try {
                showDialog("Subiendo foto...")
                mImageFileProfile = FileUtil().from(this, Objects.requireNonNull(data)!!.data!!)
                mImageProvider.saveImage(this, mImageFileProfile!!, binding.etDni.text.toString()).addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        mImageProvider.getStorage().downloadUrl.addOnSuccessListener { uri ->
                            urlImageProfile = uri.toString()
                            binding.civOpenGallery.setImageBitmap(
                                BitmapFactory.decodeFile(
                                    mImageFileProfile!!.absolutePath
                                )
                            )
                            hideDialog()
                            Toast.makeText(
                                this@RegisterActivity,
                                "Imagen subida!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    else{
                        hideDialog()
                        Toast.makeText(
                            this@RegisterActivity,
                            "Error al subir imagen",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }.addOnCanceledListener {
                    hideDialog()
                    Toast.makeText(
                        this@RegisterActivity,
                        "Error al subir imagen",
                        Toast.LENGTH_SHORT
                    ).show()
                }.addOnFailureListener {
                    hideDialog()
                    Toast.makeText(
                        this@RegisterActivity,
                        "Error al subir imagen",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }catch (e: Exception){
                hideDialog()
                Toast.makeText(
                    this@RegisterActivity,
                    "Error al subir imagen",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun openGallery() {
        if (binding.etFullName.text.toString().isNotEmpty()){
            val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
            galleryIntent.type = "image/*"
            startActivityForResult(galleryIntent, GALLERY_REQUEST)
        }
        else{
            Toast.makeText(this, "Ingrese su DNI...", Toast.LENGTH_SHORT).show()
        }

    }

}
