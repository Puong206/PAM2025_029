package com.example.railsensus.modeldata

import kotlinx.serialization.Serializable

data class LoginRequest(
    val email: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val id: Int,
    val username: String,
    val email: String,
    val role: String,
    val accessToken: String
)

data class DetailLogin(
    val email: String = "",
    val password: String = ""
)

data class UILoginState(
    val loginData: DetailLogin = DetailLogin(),
    val isEntryValid: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)