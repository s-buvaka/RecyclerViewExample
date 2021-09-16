package com.solvery.recyclerviewexample.data.repo

import com.solvery.recyclerviewexample.data.models.StoriesResponse
import com.solvery.recyclerviewexample.data.network.NetworkConstants
import com.solvery.recyclerviewexample.data.network.NyTimesApi
import com.solvery.recyclerviewexample.data.network.StoriesCategory
import retrofit2.Call

interface StoriesRepository {

    fun getStories(): Call<StoriesResponse>
}

internal class StoriesRepositoryImpl(private val api: NyTimesApi) : StoriesRepository {

    override fun getStories(): Call<StoriesResponse> {
        return api.getPersons(StoriesCategory.ARTS.category, NetworkConstants.API_KEY)
    }
}