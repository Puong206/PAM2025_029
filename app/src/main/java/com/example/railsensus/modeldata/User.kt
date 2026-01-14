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

