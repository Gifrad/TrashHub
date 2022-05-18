package com.capstone.project.trashhub

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.capstone.project.trashhub.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var imageUri: Uri
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseDatabase
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()
        database = Firebase.database.reference
        setupAction()
    }

    private fun setupAction() {
        db = Firebase.database
        val profile = db.reference.child(PROFILE)
        val user = auth.currentUser
        val name = binding.nameEdtText.setText(user?.displayName).toString()
        val email = binding.emailEdtText.setText(user?.email).toString()

        binding.imgProfile.setOnClickListener {
            intentCamera()
        }
        binding.btnSimpan.setOnClickListener {
            val image = when{
                ::imageUri.isInitialized -> imageUri
                user?.photoUrl == null -> Uri.parse(R.drawable.icon_camera.toString())
                else -> user.photoUrl }

            val updateProfile = Profile(
                name,
                email,
                image.toString(),
                binding.jenisKelaminEdtText.text.toString(),
                binding.alamatEdtText.text.toString(),
                binding.noHpEdtText.text.toString()

            )
            profile.push().setValue(updateProfile) { error, _ ->
                if (error != null) {
                    Toast.makeText(this, "Gagal menyimpan" + error.message, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this, "Berhasil menyimpan", Toast.LENGTH_SHORT).show()
                }

            }

        }
    }



    @SuppressLint("QueryPermissionsNeeded")
    private fun intentCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { intent ->
            packageManager?.let {
                intent.resolveActivity(it).also {
                    startActivityForResult(intent, REQUEST_CAMERA)
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            uploadImage(imageBitmap)
        }
    }


    private fun uploadImage(imageBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref =
            FirebaseStorage.getInstance().reference.child("img/${FirebaseAuth.getInstance().currentUser?.uid}")

        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val image: ByteArray = baos.toByteArray()

        ref.putBytes(image)
            .addOnCompleteListener { upload ->
                if (upload.isSuccessful) {
                    ref.downloadUrl.addOnCompleteListener { task ->
                        task.result?.let { uri ->
                            imageUri = uri
                            binding.imgProfile.setImageBitmap(imageBitmap)
                        }
                    }
                }
            }
    }

    companion object {
        const val REQUEST_CAMERA = 100
        const val PROFILE = "profile"
    }

}