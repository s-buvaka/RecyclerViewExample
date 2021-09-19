package com.solvery.recyclerviewexample.data.models

import kotlinx.serialization.Serializable

@Serializable
data class MultimediaItem(
    val copyright: String? = null,
    val subtype: String? = null,
    val format: String? = null,
    val width: Int? = null,
    val caption: String? = null,
    val type: String? = null,
    val url: String? = null,
    val height: Int? = null
)
