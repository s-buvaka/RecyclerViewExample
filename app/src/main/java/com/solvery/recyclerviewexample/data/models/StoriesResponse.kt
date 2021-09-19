package com.solvery.recyclerviewexample.data.models

import kotlinx.serialization.Serializable

@Serializable
data class StoriesResponse(
    val copyright: String? = null,
    val lastUpdated: String? = null,
    val section: String? = null,
    val results: List<ResultsItem>? = null,
    val numResults: Int? = null,
    val status: String? = null
)
