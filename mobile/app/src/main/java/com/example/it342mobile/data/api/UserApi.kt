package com.example.it342mobile.data.api

import retrofit2.Call
import com.example.it342mobile.data.model.UserProfile
import retrofit2.http.GET
import retrofit2.http.Header

interface UserApi {

    @GET("api/user/me")
    fun getMe(
        @Header("Authorization") token: String
    ): Call<UserProfile>
}