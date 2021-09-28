package com.solvery.recyclerviewexample.data.repo

import com.solvery.recyclerviewexample.application.executors.Executors
import com.solvery.recyclerviewexample.data.databse.StoriesDatabase
import com.solvery.recyclerviewexample.data.domain.ResultListener
import com.solvery.recyclerviewexample.data.domain.models.Story
import com.solvery.recyclerviewexample.data.network.NetworkConstants
import com.solvery.recyclerviewexample.data.network.NyTimesApi
import com.solvery.recyclerviewexample.data.network.StoriesCategory
import com.solvery.recyclerviewexample.data.network.models.StoriesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface StoriesRepository {

    fun getStories(resultListener: ResultListener<List<Story>>)
}

internal class StoriesRepositoryImpl(
    private val executors: Executors,
    private val api: NyTimesApi,
    private val database: StoriesDatabase
) : StoriesRepository {

    override fun getStories(resultListener: ResultListener<List<Story>>) {
        executors.background.execute {
            val dbStories: List<Story> = database.storiesDao().getAll()

            if (dbStories.isNotEmpty()) {
                resultListener.onSuccess(dbStories)
            } else {
                getFromRemote(resultListener)
            }
        }
    }

    private fun getFromRemote(resultListener: ResultListener<List<Story>>) =
        api.getPersons(StoriesCategory.ARTS.category, NetworkConstants.API_KEY)
            .enqueue(object : Callback<StoriesResponse> {
                override fun onResponse(
                    call: Call<StoriesResponse>,
                    response: Response<StoriesResponse>
                ) {
                    val results = response.body()?.results
                    if (response.isSuccessful && !results.isNullOrEmpty()) {
                        val stories = results.map { resultsItem ->
                            Story(
                                url = resultsItem.url.orEmpty(),
                                title = resultsItem.title.orEmpty(),
                                byline = resultsItem.byline.orEmpty(),
                                section = resultsItem.section.orEmpty()
                            )
                        }
                        resultListener.onSuccess(stories)

                        executors.background.execute { database.storiesDao().insertAll(stories) }
                    }
                }

                override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                    resultListener.onError(t)
                }

            })
}