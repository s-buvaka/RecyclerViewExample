package com.solvery.recyclerviewexample.data.databse

import androidx.room.Room
import com.solvery.recyclerviewexample.application.App

object DatabaseHolder {

    private const val DATABASE_NAME = "stories_database"

    val storiesDatabase: StoriesDatabase by lazy { createDatabase() }

    private fun createDatabase(): StoriesDatabase = Room.databaseBuilder(
        App.applicationContext(),
        StoriesDatabase::class.java,
        DATABASE_NAME
    ).build()
}