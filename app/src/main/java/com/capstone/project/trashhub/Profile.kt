package com.capstone.project.trashhub

<<<<<<< HEAD
=======
import com.google.firebase.database.Exclude
>>>>>>> 3ddcdb1 (mengganti firebase database menjadi firestore)
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Profile(
<<<<<<< HEAD
    val name: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val jenisKelamin: String? = null,
    val alamat : String? = null,
    val noHp : String? = null
=======
    var id : String? = null,
    var photoUrl: String? = null,
//    val isJenisKelamin: Boolean? = null,
    var jenisKelamin: String? = null,
    var alamat : String? = null,
    var noHp : String? = null
>>>>>>> 3ddcdb1 (mengganti firebase database menjadi firestore)
)
