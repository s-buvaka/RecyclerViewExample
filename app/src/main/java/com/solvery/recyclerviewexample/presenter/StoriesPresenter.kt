package com.solvery.recyclerviewexample.presenter

import android.annotation.SuppressLint
import android.os.Handler
import com.solvery.recyclerviewexample.data.network.models.StoriesResponse
import com.solvery.recyclerviewexample.ui.models.StoryVO
import com.solvery.recyclerviewexample.data.repo.StoriesRepository
import com.solvery.recyclerviewexample.ui.StoriesView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class StoriesPresenter(private val storiesRepository: StoriesRepository) {

    private var view: StoriesView? = null

    private lateinit var stories: List<StoryVO>

    fun attach(view: StoriesView) {
        this.view = view
    }

    fun detach() {
        view = null
    }

    @SuppressLint("NewApi")
    fun loadData() {
        storiesRepository.getStories().enqueue(object : Callback<StoriesResponse> {
            override fun onResponse(
                call: Call<StoriesResponse>,
                response: Response<StoriesResponse>
            ) {
                val results = response.body()?.results
                if (response.isSuccessful && !results.isNullOrEmpty()) {
                    val stories = results.map { resultsItem ->
                        StoryVO(
                            url = resultsItem.url.orEmpty(),
                            title = resultsItem.title.orEmpty(),
                            byline = resultsItem.byline.orEmpty(),
                            section = resultsItem.section.orEmpty()
                        )
                    }

                    view?.updateStories(stories)
                }
            }

            override fun onFailure(call: Call<StoriesResponse>, t: Throwable) {
                view?.showMessage("Something is wrong")
            }
        })

    }

    fun onUserClick(story: StoryVO) {
        view?.showLoader(true)
        doSomethingWithUser(story)
    }

    fun handleQuery(query: CharSequence) {
        val filteredList = stories.filter { user ->
            user.title.contains(query, true) ||
                    user.byline.contains(query, true) ||
                    "${user.title} ${user.byline}".contains(query, true)
        }

        view?.updateStories(filteredList)
    }

    private fun doSomethingWithUser(story: StoryVO) {
        Handler().postDelayed({
            view?.showLoader(false)
            view?.showMessage("${story.title} ${story.byline} was clicked")
        }, Random.nextLong(2000))
    }
}