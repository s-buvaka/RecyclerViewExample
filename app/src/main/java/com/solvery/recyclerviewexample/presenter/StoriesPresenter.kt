package com.solvery.recyclerviewexample.presenter

import android.annotation.SuppressLint
import android.os.Handler
import com.solvery.recyclerviewexample.data.domain.ResultListener
import com.solvery.recyclerviewexample.data.domain.models.Story
import com.solvery.recyclerviewexample.data.repo.StoriesRepository
import com.solvery.recyclerviewexample.ui.StoriesView
import kotlin.random.Random

class StoriesPresenter(private val storiesRepository: StoriesRepository) {

    private var view: StoriesView? = null

    private var stories: List<Story> = emptyList()

    fun attach(view: StoriesView) {
        this.view = view
    }

    fun detach() {
        view = null
    }

    @SuppressLint("NewApi")
    fun loadData() {
        storiesRepository.getStories(object : ResultListener<List<Story>> {
            override fun onSuccess(data: List<Story>) {
                stories = data
                view?.updateStories(data)
            }

            override fun onError(error: Throwable) {
                view?.showMessage("Something is wrong")
            }
        })
    }

    fun onUserClick(story: Story) {
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

    private fun doSomethingWithUser(story: Story) {
        Handler().postDelayed({
            view?.showLoader(false)
            view?.showMessage("${story.title} ${story.byline} was clicked")
        }, Random.nextLong(2000))
    }
}