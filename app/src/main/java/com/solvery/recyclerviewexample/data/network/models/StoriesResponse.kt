package com.solvery.recyclerviewexample.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StoriesResponse(
    @SerialName("copyright") val copyright: String? = null,
    @SerialName("lastUpdated") val lastUpdated: String? = null,
    @SerialName("section") val section: String? = null,
    @SerialName("results") val results: List<ResultsItem>? = null,
    @SerialName("numResults") val numResults: Int? = null,
    @SerialName("status") val status: String? = null
)
