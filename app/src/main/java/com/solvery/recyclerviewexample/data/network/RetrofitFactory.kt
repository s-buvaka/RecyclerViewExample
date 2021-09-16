package com.solvery.recyclerviewexample.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

@ExperimentalSerializationApi
object RetrofitFactory {

    private const val BASE_URL = "https://api.nytimes.com/svc/topstories/v2/"

    private val retrofit: Retrofit by lazy { buildRetrofit() }

    val nyTimesApi: NyTimesApi by lazy { retrofit.create(NyTimesApi::class.java) }

    private fun buildRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()
        val converterFactory = Json {
            ignoreUnknownKeys = true
        }.asConverterFactory(contentType)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(converterFactory)
            .build()
    }
}