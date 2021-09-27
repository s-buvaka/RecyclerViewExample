package com.solvery.recyclerviewexample.ui

import com.solvery.recyclerviewexample.ui.models.StoryVO

interface StoriesView {

    fun showLoader(isShow: Boolean)

    fun showMessage(message: String)

    fun updateStories(stories: List<StoryVO>)
}