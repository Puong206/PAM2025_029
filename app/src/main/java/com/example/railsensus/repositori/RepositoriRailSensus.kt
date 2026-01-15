package com.example.railsensus.repositori

import com.example.railsensus.apiservice.ServiceApi
import com.example.railsensus.modeldata.CreateKeretaRequest
import com.example.railsensus.modeldata.CreateLokoRequest
import com.example.railsensus.modeldata.CreateSensusRequest
import com.example.railsensus.modeldata.LoginRequest
import com.example.railsensus.modeldata.RegisterRequest

class RepositoriRailSensus(
    private val serviceApi: ServiceApi
) {
    //Auth
    suspend fun login(email: String, password: String) = safeApiCall {
        serviceApi.login(LoginRequest(email, password))
    }

    suspend fun register(username: String, email: String, password: String) =
        safeApiCall {
            serviceApi.register(RegisterRequest(username, email, password))
    }

    suspend fun getProfile(token: String) = safeApiCall {
        serviceApi.getProfile(token)
    }

    //Loko
    suspend fun getAllLoko() = safeApiCall {
        serviceApi.getAllLoko()
    }

    suspend fun getLokoById(id: Int) = safeApiCall {
        serviceApi.getLokoById(id)
    }

    suspend fun searchLoko(query: String) = safeApiCall {
        serviceApi.searchLoko(query)
    }

    suspend fun getLokoStatistik() = safeApiCall {
        serviceApi.getLokoStatistik()
    }

    suspend fun createLoko(token: String, data: CreateLokoRequest) = safeApiCall {
        serviceApi.createLoko(token, data)
    }

    suspend fun updateLoko(token: String, id: Int, data: CreateLokoRequest) = safeApiCall {
        serviceApi.updateLoko(id, token, data)
    }

    suspend fun deleteLoko(token: String, id: Int) = safeApiCall {
        serviceApi.deleteLoko(id, token)
    }

    //Kereta
    suspend fun getAllKereta() = safeApiCall {
        serviceApi.getAllKereta()
    }

    suspend fun getKeretaById(id: Int) = safeApiCall {
        serviceApi.getKeretaById(id)
    }

    suspend fun searchKereta(query: String) = safeApiCall {
        serviceApi.searchKereta(query)
    }

    suspend fun getKeretaStatistik() = safeApiCall {
        serviceApi.getKeretaStatistik()
    }

    suspend fun createKereta(token: String, data: CreateKeretaRequest) = safeApiCall {
        serviceApi.createKereta(token, data)
    }

    suspend fun updateKereta(token: String, id: Int, data: CreateKeretaRequest) = safeApiCall {
        serviceApi.updateKereta(id, token, data)
    }

    suspend fun deleteKereta(token: String, id: Int) = safeApiCall {
        serviceApi.deleteKereta(id, token)
    }
    
    //Sensus
    suspend fun getAllSensus(page: Int = 1, limit: Int = 20) = safeApiCall { 
        serviceApi.getAllSensus(page, limit)
    }
    
    suspend fun getSensusById(id: Int) = safeApiCall { 
        serviceApi.getSensusById(id)
    }
    
    suspend fun createSensus(token: String, data: CreateSensusRequest) = safeApiCall { 
        serviceApi.createSensus(token, data)
    }
    
    suspend fun updateSensus(token: String, id: Int, data: CreateSensusRequest) = safeApiCall { 
        serviceApi.updateSensus(id, token, data)
    }

    suspend fun deleteSensus(token: String, id: Int) =  safeApiCall {
        serviceApi.deleteKereta(id, token)
    }
}
