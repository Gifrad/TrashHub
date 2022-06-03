package com.capstone.project.trashhub

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.project.trashhub.network.ApiConfig
import com.capstone.project.trashhub.network.model.AllStoryResponse
import com.capstone.project.trashhub.network.model.ListStoryUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

/*    private val _apiMess = MutableLiveData<String>()
    val apiMess : LiveData<String> = _apiMess*/

//        FUNGSI 1
    val listUser = MutableLiveData<ArrayList<ListStoryUser>>()


    fun getAllListStory() {
        val listStory = ArrayList<ListStoryUser>()
        val client = ApiConfig.getApiService().getAllStory("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXRGUkxmTjk0WGJqdjhHT2EiLCJpYXQiOjE2NTMxNDgwMTN9.P5Tda4xE6EkL55AfMa0gylmV8DqdkvNAy5RlOq7VT08", 1)

        client.enqueue(object : Callback<AllStoryResponse> {
            override fun onResponse(
                call: Call<AllStoryResponse>,
                response: Response<AllStoryResponse>
            ) {
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null){
                    Log.d("MainActivity","Token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXRGUkxmTjk0WGJqdjhHT2EiLCJpYXQiOjE2NTMxNDgwMTN9.P5Tda4xE6EkL55AfMa0gylmV8DqdkvNAy5RlOq7VT08")
                    listUser.postValue(responseBody.listStory)
                    listUser.value = listStory
                }else{
                    Log.d("MainActivity", "Token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXRGUkxmTjk0WGJqdjhHT2EiLCJpYXQiOjE2NTMxNDgwMTN9.P5Tda4xE6EkL55AfMa0gylmV8DqdkvNAy5RlOq7VT08")
//                    _apiMess.value = response.body()?.message
                    Log.e("MainActivity", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AllStoryResponse>, t: Throwable) {
                Log.d("MainActivity :", "Token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXRGUkxmTjk0WGJqdjhHT2EiLCJpYXQiOjE2NTMxNDgwMTN9.P5Tda4xE6EkL55AfMa0gylmV8DqdkvNAy5RlOq7VT08")
//                _apiMess.value = t.message
                Log.e("MainActivity","onFailure: ${t.message}")
            }

        })
    }
}