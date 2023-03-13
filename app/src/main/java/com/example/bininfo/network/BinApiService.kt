package com.example.bininfo.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://lookup.binlist.net/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
    .baseUrl(BASE_URL)
    .build()

interface BinApiService {
    @GET("{bin}")
    suspend fun getBinInfo(@Path("bin", encoded = true) bin: String): BinCard
}

object BinApi{
    val retrofitService: BinApiService by lazy {
        retrofit.create(BinApiService::class.java)
    }
}