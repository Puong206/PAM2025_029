package com.example.railsensus.modeldata

import kotlinx.serialization.Serializable

@Serializable
data class Lokomotif(
    val loko_id: Int,
    val nomor_seri: String,
    val dipo_induk: String,
    val status: String?
)

@Serializable
data class StatistikLoko(
    val total: Int,
    val total_dipo: Int
)

//Create / Update
data class CreateLokoRequest(
    val nomor_seri: String,
    val dipo_induk: String,
    val status: String? = null
)

@Serializable
data class CreateLokoResponse(
    val message: String,
    val loko_id: Int
)

data class DetailLokomotif(
    val loko_id: Int = 0,
    val nomor_seri: String = "",
    val dipo_induk: String = "",
    val status: String = ""
)

data class UILokomotifState(
    val lokomotif: DetailLokomotif = DetailLokomotif(),
    val isEntryValid: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

fun DetailLokomotif.toCreateRequest() = CreateLokoRequest(
    nomor_seri = nomor_seri,
    dipo_induk = dipo_induk,
    status = status.ifEmpty { null }
)

fun Lokomotif.toDetailLokomotif() = DetailLokomotif(
    loko_id = loko_id,
    nomor_seri = nomor_seri,
    dipo_induk = dipo_induk,
    status = status ?: ""
)

fun Lokomotif.toUILokomotifState(isEntryValid: Boolean = false) = UILokomotifState(
    lokomotif = this.toDetailLokomotif(),
    isEntryValid = isEntryValid
)

fun DetailLokomotif.isValid(): Boolean {
    return nomor_seri.isNotEmpty() &&
            dipo_induk.isNotEmpty()
}