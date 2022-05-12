package com.capstone.project.trashhub

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.project.trashhub.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        supportActionBar?.hide()

        setupAction()
        showLoading(false)

    }

    private fun setupAction() {
        binding.btnRegister.setOnClickListener {
            showLoading(true)
            val name: String = binding.nameEdtText.text.toString().trim()
            val email: String = binding.emailEdtText.text.toString().trim()
            val pass: String = binding.passwordEdtText.text.toString().trim()
            val confPass: String = binding.confPassEdtText.text.toString().trim()

            when {

                name.isEmpty() -> {
                    showLoading(false)
                    binding.nameEdtText.error = "Nama tidak boleh kosong"
                    binding.nameEdtText.requestFocus()
                    return@setOnClickListener
                }
                email.isEmpty() -> {
                    showLoading(false)
                    binding.emailEdtText.error = "Email tidak boleh kosong"
                    binding.emailEdtText.requestFocus()
                    return@setOnClickListener
                }
                pass.isEmpty() -> {
                    showLoading(false)
                    binding.passwordEdtText.error = "Password tidak boleh kosong"
                    binding.passwordEdtText.requestFocus()
                    return@setOnClickListener
                }
                confPass.isEmpty() || confPass != pass -> {
                    showLoading(false)
                    binding.confPassEdtText.error = "Silahkan masukan password yang sama"
                    binding.confPassEdtText.requestFocus()
                    return@setOnClickListener
                }
            }
            registerUser(name,email, pass)
        }
        binding.btnLogin.setOnClickListener {
            showLoading(true)
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }

    private fun registerUser(name: String, email: String, pass: String) {
        auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) {
                showLoading(false)
                if (it.isSuccessful) {
                    val user = Firebase.auth.currentUser
                    val profileUpdate = userProfileChangeRequest {
                        displayName = name
                    }
                    user!!.updateProfile(profileUpdate)
                        .addOnCompleteListener{ task ->
                            if(task.isSuccessful){
                                Log.d(TAG, "Name Terdaftar")
                            }
                        }
                    Intent(
                        this@RegisterActivity,
                        LoginActivity::class.java
                    ).also { intent ->
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                    Toast.makeText(this, "Berhasil Mendaftar", Toast.LENGTH_SHORT).show()
                } else {
                    showLoading(false)
                    Toast.makeText(this, "Gagal Mendaftar", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            Intent(
                this@RegisterActivity,
                MainActivity::class.java
            ).also { intent ->
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}