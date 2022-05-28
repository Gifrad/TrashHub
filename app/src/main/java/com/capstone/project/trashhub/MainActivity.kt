package com.capstone.project.trashhub

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.capstone.project.trashhub.databinding.ActivityMainBinding
import com.capstone.project.trashhub.network.model.ListStoryUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        supportActionBar?.hide()
        showLoading(false)
        userValidation()
        setupAction()
        setupViewModel()
    }


    private fun setupAction() {
        binding.imgLogout.setOnClickListener {
            showLoading(true)
            signOut()
        }

        binding.imgProfile.setOnClickListener {
            startActivity(Intent(this,ProfileActivity::class.java))
        }

        binding.etSearch.setOnClickListener{
            startActivity(Intent(this,SearchActivity::class.java))
        }

        binding.iconMaps.setOnClickListener {
            startActivity(Intent(this,MapsActivity::class.java))
        }
    }


    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        mainViewModel.listUser.observe(this){
            getAdapter(it)

        }

        mainViewModel.getAllListStory("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXRGUkxmTjk0WGJqdjhHT2EiLCJpYXQiOjE2NTMxNDgwMTN9.P5Tda4xE6EkL55AfMa0gylmV8DqdkvNAy5RlOq7VT08")

        mainViewModel.apiMess.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getAdapter(listStory : ArrayList<ListStoryUser>){
        val adapter = ListBankSampahAdapter(listStory)
        binding.apply {
            rvRecomendasi.layoutManager = GridLayoutManager(this@MainActivity, 2)
            rvRecomendasi.setHasFixedSize(true)
            rvRecomendasi.adapter = adapter
        }
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