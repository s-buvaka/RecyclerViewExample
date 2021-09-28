package com.solvery.recyclerviewexample.data.domain.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "story")
data class Story(

    @PrimaryKey
    @ColumnInfo(name = "url")
    val url: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "byline")
    val byline: String,

    @ColumnInfo(name = "section")
    val section: String
)