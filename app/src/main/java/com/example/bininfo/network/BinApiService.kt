package com.example.bininfo.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://lookup.binlist.net/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface BinApiService {
    @GET("{BIN}")
    suspend fun getBinInfo(@Path("BIN", encoded = true) BIN: String): String
}

object BinApi{
    val retrofitService: BinApiService by lazy {
        retrofit.create(BinApiService::class.java)
    }
}