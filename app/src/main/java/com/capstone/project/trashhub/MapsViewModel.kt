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

class MapsViewModel : ViewModel() {

    private val _listUser = MutableLiveData<List<ListStoryUser>>()
    val listStory : LiveData<List<ListStoryUser>> get() = _listUser

    fun getBankSampahLocation(){
            val storyList = ArrayList<ListStoryUser>()

            val client = ApiConfig.getApiService().getAllStory("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXRGUkxmTjk0WGJqdjhHT2EiLCJpYXQiOjE2NTMxNDgwMTN9.P5Tda4xE6EkL55AfMa0gylmV8DqdkvNAy5RlOq7VT08", 1)

            client.enqueue(object : Callback<AllStoryResponse> {
                override fun onResponse(call: Call<AllStoryResponse>, response: Response<AllStoryResponse>) {
                    val responseBody = response.body()
                    if(response.isSuccessful && responseBody != null){
                        Log.d("MapsActivity",responseBody.toString())
                        _listUser.postValue(responseBody.listStory)
                        _listUser.value = storyList
                    }else{
                        Log.d("MapsActivity","onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<AllStoryResponse>, t: Throwable) {
                    Log.e("MapsActivity", "onFailure: ${t.message}")
                }
            })

    }

}