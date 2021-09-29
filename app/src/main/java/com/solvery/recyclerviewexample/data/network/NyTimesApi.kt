package com.solvery.recyclerviewexample.data.network

import com.solvery.recyclerviewexample.data.network.models.StoriesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NyTimesApi {

    @GET("{category}.json")
    fun getPersonsByCategory(
        @Path("category") category: String,
    ): Call<StoriesResponse>
}