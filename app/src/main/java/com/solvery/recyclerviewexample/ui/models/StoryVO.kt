package com.solvery.recyclerviewexample.ui.models

data class StoryVO(
    val url: String,
    val title: String,
    val byline: String,
    val section: String
) : VisualObject{

    override val id: String
        get() = url
}