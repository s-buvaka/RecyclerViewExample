package com.solvery.recyclerviewexample.data.network

import com.solvery.recyclerviewexample.data.models.StoriesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NyTimesApi {

    @GET("{category}.json")
    fun getPersons(
        @Path("category") category: String,
        @Query("api-key") key: String,
    ): Call<StoriesResponse>
}