package com.example.railsensus.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class Sensus(
    val sensus_id: Int,
    val user_id: Int,
    val loko_id: Int,
    val ka_id: Int,
    val trust_score: Int,
    val waktu_sensus: String,

    //dari backend
    val username: String? = null,
    val nomor_seri: String? = null,
    val nama_ka: String? = null,
    val valid_votes: Int? = null,
    val invalid_votes: Int? = null
)

// Create
data class CreateSensusRequest(
    val loko_id: Int,
    val ka_id: Int
)

@Serializable
data class CreateSensusResponse(
    val message: String,
    val sensus_id: Int
)

data class DetailSensus(
    val sensus_id: Int = 0,
    val loko_id: Int = 0,
    val ka_id: Int = 0,
    val nomor_seri: String = "",
    val nama_ka: String = ""
)

data class UISensusState(
    val sensusDetail: DetailSensus = DetailSensus(),
    val isEntryValid: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)