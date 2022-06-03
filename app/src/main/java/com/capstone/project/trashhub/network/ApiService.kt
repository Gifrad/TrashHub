package com.capstone.project.trashhub.network

import com.capstone.project.trashhub.network.model.AllStoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    @GET("stories")
    fun getAllStory(
        @Header("Authorization") token: String,
        @Query("location")location: Int
    ) : Call<AllStoryResponse>
}