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