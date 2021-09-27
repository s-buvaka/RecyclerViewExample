package com.solvery.recyclerviewexample.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class ResultsItem(
    @SerialName("perFacet") val perFacet: List<String>? = null,
    @SerialName("subsection") val subsection: String? = null,
    @SerialName("itemType") val itemType: String? = null,
    @SerialName("orgFacet") val orgFacet: List<String>? = null,
    @SerialName("section") val section: String? = null,
    @SerialName("jsonMemberAbstract") val jsonMemberAbstract: String? = null,
    @SerialName("title") val title: String? = null,
    @SerialName("desFacet") val desFacet: List<String>? = null,
    @SerialName("uri") val uri: String? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("shortUrl") val shortUrl: String? = null,
    @SerialName("materialTypeFacet") val materialTypeFacet: String? = null,
    @SerialName("multimedia") val multimedia: List<MultimediaItem>? = null,
    @SerialName("updatedDate") val updatedDate: String? = null,
    @SerialName("createdDate") val createdDate: String? = null,
    @SerialName("byline") val byline: String? = null,
    @SerialName("publishedDate") val publishedDate: String? = null,
    @SerialName("kicker") val kicker: String? = null
)
