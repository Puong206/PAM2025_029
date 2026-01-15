package com.example.railsensus.repositori

import kotlinx.serialization.json.Json
import com.example.railsensus.apiservice.ServiceApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

interface AppContainer{
    val serviceApi: ServiceApi
}

class ContainerApp: AppContainer{
    private val BASE_URL = "http://10.0.2.2:3000/"

    //Konfigurassi JSON untuk serialization
    private val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    //Logging Interceptor untuk debugging
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    //OkHttp Client dengan timeout & logging
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
    }
}