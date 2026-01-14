package com.example.railsensus.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class Kereta(
    val ka_id: Int,
    val nama_ka: String,
    val no_ka: String,
)

@Serializable
data class StatistikKereta(
    val total: Int
)

//Create / Update
data class CreateKeretaRequest(
    val nama_ka: String,
    val no_ka: String,
)

@Serializable
data class CreateKeretaResponse(
    val message: String,
    val ka_id: Int
)

data class DetailKereta(
    val ka_id: Int = 0,
    val nama_ka: String = "",
    val no_ka: String = "",
)

data class UIKeretaState(
    val keretaDetail: DetailKereta = DetailKereta(),
    val isEntryValid: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

fun DetailKereta.toCreateRequest() = CreateKeretaRequest(
    nama_ka = nama_ka,
    no_ka = no_ka,
)

fun Kereta.toDetailKereta() = DetailKereta(
    ka_id = ka_id,
    nama_ka = nama_ka,
    no_ka = no_ka
)