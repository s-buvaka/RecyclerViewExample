package com.solvery.recyclerviewexample.ui

import com.solvery.recyclerviewexample.ui.models.VisualObject

interface StoriesView {

    fun showLoader(isShow: Boolean)

    fun showMessage(message: String)

    fun updateStories(stories: List<VisualObject>)
}