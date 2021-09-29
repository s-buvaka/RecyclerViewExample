package com.solvery.recyclerviewexample.ui.stories

import com.solvery.recyclerviewexample.ui.models.VisualObject

interface StoriesView {

    fun showLoader(isShow: Boolean)

    fun showMessage(message: String)

    fun updateStories(stories: List<VisualObject>)
}