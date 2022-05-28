package com.capstone.project.trashhub.network.model

import com.google.gson.annotations.SerializedName

data class AllStoryResponse(

    @field:SerializedName("listStory")
    val listStory:ArrayList<ListStoryUser>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String

)
