package com.example.railsensus.apiservice

import com.example.railsensus.modeldata.LoginRequest
import com.example.railsensus.modeldata.LoginResponse
import com.example.railsensus.modeldata.RegisterRequest
import com.example.railsensus.modeldata.RegisterResponse
import com.example.railsensus.modeldata.UserProfile
import retrofit2.Response
import retrofit2.http.*

interface ServiceApi {
    // Auth
    @POST("api/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    @POST("api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @GET("api/auth/me")
    suspend fun getProfile(
        @Header("x-acces-token") token: String
    ): Response<UserProfile>
}