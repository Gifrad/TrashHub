package com.capstone.project.trashhub.network

import com.capstone.project.trashhub.network.model.AllStoryResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {
    @GET("stories")
    fun getAllStory(
        @Header("Authorization") token: String
    ) : Call<AllStoryResponse>
}