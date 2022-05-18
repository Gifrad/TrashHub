package com.capstone.project.trashhub

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.project.trashhub.data.PROFILE_NAME
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception
//import kotlin.Exception

class ProfileViewModel : ViewModel() {

    private val _result  = MutableLiveData<Exception?>()
    val result :LiveData<Exception?>
        get() = _result

    fun addProfile(profile: Profile,id:String){
        val dbProfile = FirebaseDatabase.getInstance().getReference(PROFILE_NAME)
        profile.id = id
        dbProfile.child(profile.id!!).setValue(profile)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    _result.value = null
                }else{
                    _result.value = it.exception
                }
            }
    }
}