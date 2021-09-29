package com.solvery.recyclerviewexample.data.repo

import com.solvery.recyclerviewexample.application.executors.Executors
import com.solvery.recyclerviewexample.data.databse.StoriesDatabase
import com.solvery.recyclerviewexample.data.domain.ResultListener
import com.solvery.recyclerviewexample.data.domain.mappers.Mapper
import com.solvery.recyclerviewexample.data.domain.models.Story
import com.solvery.recyclerviewexample.data.network.NyTimesApi
import com.solvery.recyclerviewexample.data.network.models.ResultsItem
import com.solvery.recyclerviewexample.data.network.models.StoriesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface StoriesRepository {

    fun getStories(
        forceUpdate: Boolean = false,
        section: String,
        resultListener: ResultListener<List<Story>>
    )
}

internal class StoriesRepositoryImpl(
    private val executors: Executors,
    private val api: NyTimesApi,
    private val database: StoriesDatabase,
    private val storyMapper: Mapper<ResultsItem, Story>
) : StoriesRepository {

    override fun getStories(
        forceUpdate: Boolean,
        section: String,
        resultListener: ResultListener<List<Story>>
    ) {
        if (forceUpdate) {
            getFromRemote(section, resultListener)
        } else {
            executors.background.execute {
                val dbStories: List<Story> = database.storiesDao().getAll()

                if (dbStories.isNotEmpty()) {
                    resultListener.onSuccess(dbStories)
                } else {
                    getFromRemote(section, resultListener)
                }
            }
        }
    }

    private fun getFromRemote(section: String, resultListener: ResultListener<List<Story>>) =
        api.getPersonsByCategory(section)
            .enqueue(object : Callback<StoriesResponse> {
                override fun onResponse(
                    call: Call<StoriesResponse>,
                    response: Response<StoriesResponse>
                ) {
                    val results = response.body()?.results
                    if (response.isSuccessful && !results.isNullOrEmpty()) {
                        val stories = results.map(storyMapper::map)
                        resultListener.onSuccess(stories)

                        executors.background.execute { database.storiesDao().insertAll(stories) }
                    }
                }

                override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                    resultListener.onError(t)
                }
            })
}