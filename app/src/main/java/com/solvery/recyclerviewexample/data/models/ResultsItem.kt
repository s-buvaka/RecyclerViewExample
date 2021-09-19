package com.solvery.recyclerviewexample.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ResultsItem(
    val perFacet: List<String>? = null,
    val subsection: String? = null,
    val itemType: String? = null,
    val orgFacet: List<String>? = null,
    val section: String? = null,
    val jsonMemberAbstract: String? = null,
    val title: String? = null,
    val desFacet: List<String>? = null,
    val uri: String? = null,
    val url: String? = null,
    val shortUrl: String? = null,
    val materialTypeFacet: String? = null,
    val multimedia: List<MultimediaItem>? = null,
    val updatedDate: String? = null,
    val createdDate: String? = null,
    val byline: String? = null,
    val publishedDate: String? = null,
    val kicker: String? = null
)
