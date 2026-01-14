package com.example.railsensus.apiservice

import com.example.railsensus.modeldata.ApiResponse
import com.example.railsensus.modeldata.CreateKeretaRequest
import com.example.railsensus.modeldata.CreateKeretaResponse
import com.example.railsensus.modeldata.CreateLokoRequest
import com.example.railsensus.modeldata.CreateLokoResponse
import com.example.railsensus.modeldata.CreateSensusRequest
import com.example.railsensus.modeldata.CreateSensusResponse
import com.example.railsensus.modeldata.Kereta
import com.example.railsensus.modeldata.LoginRequest
import com.example.railsensus.modeldata.LoginResponse
import com.example.railsensus.modeldata.Lokomotif
import com.example.railsensus.modeldata.RegisterRequest
import com.example.railsensus.modeldata.RegisterResponse
import com.example.railsensus.modeldata.Sensus
import com.example.railsensus.modeldata.StatistikKereta
import com.example.railsensus.modeldata.StatistikLoko
import com.example.railsensus.modeldata.StatistikUser
import com.example.railsensus.modeldata.UserManagement
import com.example.railsensus.modeldata.UserProfile
import com.example.railsensus.modeldata.VoteRequest
import com.example.railsensus.modeldata.VoteResponse
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
        @Header("x-access-token") token: String
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

    @GET("api/lokomotif/statistics")
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

    //Kereta
    @GET("api/kereta")
    suspend fun getAllKereta():
            Response<List<Kereta>>

    @GET("api/kereta/{id}")
    suspend fun getKeretaById(
        @Path("id") kaId: Int
    ): Response<Kereta>

    @GET("api/kereta/search")
    suspend fun searchKereta(
        @Query("q") query: String
    ): Response<List<Kereta>>

    @GET("api/kereta/statistics")
    suspend fun getKeretaStatistik():
            Response<StatistikKereta>

    @POST("api/kereta")
    suspend fun createKereta(
        @Header("x-access-token") token: String,
        @Body request: CreateKeretaRequest
    ): Response<CreateKeretaResponse>

    @PUT("api/kereta/{id}")
    suspend fun updateKereta(
        @Path("id") kaId: Int,
        @Header("x-access-token") token: String,
        @Body request: CreateKeretaRequest
    ): Response<ApiResponse>

    @DELETE("api/kereta/{id}")
    suspend fun deleteKereta(
        @Path("id") kaId: Int,
        @Header("x-access-token") token: String
    ): Response<ApiResponse>

    //Sensus
    @GET("api/sensus")
    suspend fun getAllSensus(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 20
    ): Response<List<Sensus>>

    @GET("api/sensus/{id}")
    suspend fun getSensusById(
        @Path("id") sensusId: Int
    ): Response<Sensus>

    @POST("api/sensus")
    suspend fun createSensus(
        @Header("x-access-token") token: String,
        @Body request: CreateSensusRequest
    ): Response<CreateSensusResponse>

    @PUT("api/sensus/{id}")
    suspend fun updateSensus(
        @Path("id") sensusId: Int,
        @Header("x-access-token") token: String,
        @Body request: CreateSensusRequest
    ): Response<ApiResponse>

    @DELETE("api/sensus/{id}")
    suspend fun deleteSensus(
        @Path("id") sensusId: Int,
        @Header("x-access-token") token: String
    ): Response<ApiResponse>

    //Voting
    @POST("api/sensus/{id}/vote")
    suspend fun voteSensus(
        @Path("id") sensusId: Int,
        @Header("x-access-token") token: String,
        @Body request: VoteRequest
    ): Response<VoteResponse>

    @DELETE("api/sensus/{id}/vote")
    suspend fun removeVote(
        @Path("id") sensusId: Int,
        @Header("x-access-token") token: String
    ): Response<ApiResponse>

    //Kelola User
    @GET("api/users")
    suspend fun getAllUsers(
        @Header("x-access-token") token: String
    ): Response<List<UserManagement>>

    @GET("api/users/statistics")
    suspend fun getUserStatistik(
        @Header("x-access-token") token: String
    ): Response<StatistikUser>

    @GET("api/users/{id}")
    suspend fun getUserById(
        @Path("id") userId: Int,
        @Header("x-access-token") token: String
    ): Response<UserManagement>

    @DELETE("api/users/{id}")
    suspend fun deleteUser(
        @Path("id") userId: Int,
        @Header("x-access-token") token: String
    ): Response<ApiResponse>
}