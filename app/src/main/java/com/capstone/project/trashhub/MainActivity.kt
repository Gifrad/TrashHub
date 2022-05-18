package com.capstone.project.trashhub

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.capstone.project.trashhub.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        supportActionBar?.hide()
        showLoading(false)
        userValidation()
        setupAction()
    }

    @SuppressLint("SetTextI18n")
    private fun userValidation() {
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            // Not signed in, launch the Login activity
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        } else {
            binding.tvUsername.text = "Hai ${firebaseUser.displayName}"
        }
    }


    //LOGOUT
    private fun setupAction() {
        binding.imgLogout.setOnClickListener {
            showLoading(true)
            signOut()
        }

        binding.imgProfile.setOnClickListener {
            startActivity(Intent(this,ProfileActivity::class.java))
        }
    }

    private fun signOut() {
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}