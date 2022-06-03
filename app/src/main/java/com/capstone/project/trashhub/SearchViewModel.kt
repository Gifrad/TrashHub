package com.capstone.project.trashhub

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.project.trashhub.network.ApiConfig
import com.capstone.project.trashhub.network.model.AllStoryResponse
import com.capstone.project.trashhub.network.model.ListStoryUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel : ViewModel() {

    val listUser = MutableLiveData<ArrayList<ListStoryUser>>()

    fun showDataSearch() {
        val listData = ArrayList<ListStoryUser>()
        ApiConfig.getApiService()
            .getAllStory("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXRGUkxmTjk0WGJqdjhHT2EiLCJpYXQiOjE2NTMxNDgwMTN9.P5Tda4xE6EkL55AfMa0gylmV8DqdkvNAy5RlOq7VT08",1)
            .enqueue(object : Callback<AllStoryResponse> {
                override fun onResponse(
                    call: Call<AllStoryResponse>,
                    response: Response<AllStoryResponse>
                ) {
                    val dataList = response.body()
                    if (response.isSuccessful && dataList != null) {
                        Log.d("SearchActivity","Token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXRGUkxmTjk0WGJqdjhHT2EiLCJpYXQiOjE2NTMxNDgwMTN9.P5Tda4xE6EkL55AfMa0gylmV8DqdkvNAy5RlOq7VT08")
                        listUser.postValue(dataList.listStory)
                        listUser.value = listData
                    } else {
                        Log.d("SearchActivity", "Token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOiJ1c2VyLXRGUkxmTjk0WGJqdjhHT2EiLCJpYXQiOjE2NTMxNDgwMTN9.P5Tda4xE6EkL55AfMa0gylmV8DqdkvNAy5RlOq7VT08")
                        Log.e("SearchActivity", "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<AllStoryResponse>, t: Throwable) {
                    Log.e("MainActivity", "onFailure: ${t.message}")
                }

            })

    }
}