package com.capstone.project.trashhub

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Profile(
    val name: String? = null,
    val email: String? = null,
    val photoUrl: String? = null,
    val jenisKelamin: String? = null,
    val alamat : String? = null,
    val noHp : String? = null
)
