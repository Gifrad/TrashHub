package com.capstone.project.trashhub

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.capstone.project.trashhub.databinding.ActivityDetailBankSampahBinding
import com.capstone.project.trashhub.network.model.ListStoryUser

class DetailBankSampahActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailBankSampahBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityDetailBankSampahBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    companion object{
        const val EXTRA_DATA = "extra_data"
    }

}