package com.solvery.recyclerviewexample.data.databse

import androidx.room.Database
import androidx.room.RoomDatabase
import com.solvery.recyclerviewexample.data.domain.models.Story

@Database(
    entities = [
        Story::class
    ],
    version = 1
)
abstract class StoriesDatabase : RoomDatabase() {

    abstract fun storiesDao(): StoriesDao
}