package com.capstone.project.trashhub

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
<<<<<<< HEAD
=======
import com.capstone.project.trashhub.data.PROFILE_NAME
>>>>>>> 3ddcdb1 (mengganti firebase database menjadi firestore)
import com.capstone.project.trashhub.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
<<<<<<< HEAD
=======
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
>>>>>>> 3ddcdb1 (mengganti firebase database menjadi firestore)
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
<<<<<<< HEAD
=======
    private lateinit var fireStore : FirebaseFirestore
//    private lateinit var profile: Profile
>>>>>>> 3ddcdb1 (mengganti firebase database menjadi firestore)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
<<<<<<< HEAD
        supportActionBar?.hide()

=======
        fireStore = FirebaseFirestore.getInstance()
        supportActionBar?.hide()
//        FirebaseDatabase.getInstance().getReference(PROFILE_NAME)
>>>>>>> 3ddcdb1 (mengganti firebase database menjadi firestore)
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
<<<<<<< HEAD
                    val user = Firebase.auth.currentUser
                    val profileUpdate = userProfileChangeRequest {
                        displayName = name
                    }
                    user!!.updateProfile(profileUpdate)
=======
                    val userFirebase = Firebase.auth.currentUser
                    val userId = userFirebase?.uid
                    val user = hashMapOf(
                        "id" to userId,
                        "name" to name,
                        "jenisKelamin" to 0,
                        "noHp" to "",
                        "photoUrl" to "",
                        "alamat" to "",
                        "poin" to ""
                    )
                    fireStore.collection(PROFILE_NAME)
                        .add(user)
                        .addOnSuccessListener { documentReference ->
                            Log.d(TAG, "registerUser with ID: ${documentReference.id}")
                        }
                        .addOnFailureListener {e->
                            Log.e(TAG, "registerUser: Error ", e)
                        }
//                    val profile = Profile()
//                    profile.id = user?.uid.toString()
                    val profileUpdate = userProfileChangeRequest {
                        displayName = name
                    }
                    userFirebase!!.updateProfile(profileUpdate)
>>>>>>> 3ddcdb1 (mengganti firebase database menjadi firestore)
                        .addOnCompleteListener{ task ->
                            if(task.isSuccessful){
                                Log.d(TAG, "Name Terdaftar")
                            }
                        }
<<<<<<< HEAD
=======
//                    dbProfile.child(profile.id!!).setValue(profile)
>>>>>>> 3ddcdb1 (mengganti firebase database menjadi firestore)
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