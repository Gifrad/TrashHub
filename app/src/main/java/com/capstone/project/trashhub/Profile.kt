package com.capstone.project.trashhub

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Profile(
    var id : String? = null,
    var photoUrl: String? = null,
//    val isJenisKelamin: Boolean? = null,
    var jenisKelamin: String? = null,
    var alamat : String? = null,
    var noHp : String? = null
)
