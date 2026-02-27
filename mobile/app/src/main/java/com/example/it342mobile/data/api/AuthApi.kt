package com.example.it342mobile.data.api

import com.example.it342mobile.data.model.LoginRequest
import com.example.it342mobile.data.model.RegisterRequest
import com.example.it342mobile.data.response.JwtResponse
import com.example.it342mobile.data.response.MessageResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/auth/register")
    fun register(@Body request: RegisterRequest): Call<MessageResponse>

    @POST("api/auth/login")
    fun login(@Body request: LoginRequest): Call<JwtResponse>
}