package com.solvery.recyclerviewexample.ui

import com.solvery.recyclerviewexample.data.domain.models.Story

interface StoriesView {

    fun showLoader(isShow: Boolean)

    fun showMessage(message: String)

    fun updateStories(stories: List<Story>)
}