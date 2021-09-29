package com.solvery.recyclerviewexample.data.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

@ExperimentalSerializationApi
object RetrofitFactory {

    private const val BASE_URL = "https://api.nytimes.com/svc/topstories/v2/"
    private const val TIMEOUT_IN_SECOND = 10

    private val retrofit: Retrofit by lazy { buildRetrofit() }
    private val json: Json = Json { ignoreUnknownKeys = true }

    val nyTimesApi: NyTimesApi by lazy { retrofit.create(NyTimesApi::class.java) }

    private fun buildRetrofit(): Retrofit {
        val contentType = "application/json".toMediaType()
        val converterFactory = json.asConverterFactory(contentType)

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(buildOkHttpClient())
            .addConverterFactory(converterFactory)
            .build()
    }

    private fun buildOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(ApiKeyInterceptor())
            .readTimeout(TIMEOUT_IN_SECOND.toLong(), TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT_IN_SECOND.toLong(), TimeUnit.SECONDS)
            .build()
    }
}