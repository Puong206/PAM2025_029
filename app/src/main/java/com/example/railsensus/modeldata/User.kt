package com.example.railsensus.modeldata

import kotlinx.serialization.Serializable

//Login
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

//Register
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val role: String = "User"
)

@Serializable
data class RegisterResponse(
    val message: String,
    val user: UserData
)

@Serializable
data class UserData(
    val id: Int,
    val username: String,
    val email: String,
    val role: String
)

data class DetailRegister(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassowrd: String = ""
)

data class UIRegisterState(
    val registerData: DetailRegister = DetailRegister(),
    val isEntryValid: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

//Profile
@Serializable
data class UserProfile(
    val id: Int,
    val username: String,
    val email: String,
    val role: String,
    val created_at: String
)