package com.example.railsensus.apiservice

import androidx.room.Dao
import com.example.railsensus.modeldata.ApiResponse
import com.example.railsensus.modeldata.CreateLokoRequest
import com.example.railsensus.modeldata.CreateLokoResponse
import com.example.railsensus.modeldata.LoginRequest
import com.example.railsensus.modeldata.LoginResponse
import com.example.railsensus.modeldata.Lokomotif
import com.example.railsensus.modeldata.RegisterRequest
import com.example.railsensus.modeldata.RegisterResponse
import com.example.railsensus.modeldata.StatistikLoko
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

    //Lokomotif
    @GET("api/lokomotif")
    suspend fun getAllLoko():
            Response<List<Lokomotif>>

    @GET("api/lokomotif/{id}")
    suspend fun getLokoById(
        @Path("id") lokoId: Int
    ): Response<Lokomotif>

    @GET("api/lokomotif/search")
    suspend fun searchLoko(
        @Query("q") query: String
    ): Response<List<Lokomotif>>

    @GET("api/lokomotif/statistik")
    suspend fun getLokoStatistik():
            Response<StatistikLoko>

    @POST("api/lokomotif")
    suspend fun createLoko(
        @Header("x-access-token") token: String,
        @Body request: CreateLokoRequest
    ): Response<CreateLokoResponse>

    @PUT("api/lokomotif/{id}")
    suspend fun updateLoko(
        @Path("id") lokoId: Int,
        @Header("x-access-token") token: String,
        @Body request: CreateLokoRequest
    ): Response<ApiResponse>

    @DELETE("api/lokomotif/{id}")
    suspend fun deleteLoko(
        @Path("id") lokoId: Int,
        @Header("x-access-token") token: String
    ): Response<ApiResponse>
}