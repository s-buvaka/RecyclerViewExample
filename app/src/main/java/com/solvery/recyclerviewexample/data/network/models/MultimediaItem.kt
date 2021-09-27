package com.solvery.recyclerviewexample.data.network.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class MultimediaItem(
    @SerialName("copyright") val copyright: String? = null,
    @SerialName("subtype") val subtype: String? = null,
    @SerialName("format") val format: String? = null,
    @SerialName("width") val width: Int? = null,
    @SerialName("caption") val caption: String? = null,
    @SerialName("type") val type: String? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("height") val height: Int? = null
)
