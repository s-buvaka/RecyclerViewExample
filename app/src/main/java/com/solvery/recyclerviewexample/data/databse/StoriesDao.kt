package com.solvery.recyclerviewexample.data.databse

import androidx.room.*
import com.solvery.recyclerviewexample.data.domain.models.Story

@Dao
interface StoriesDao {

    @Query("SELECT * FROM story")
    fun getAll(): List<Story>

    @Query("SELECT * FROM story WHERE title LIKE :title")
    fun findByTitle(title: String): Story

    @Query("SELECT * FROM story WHERE section LIKE :section")
    fun findBySection(section: String): Story

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(stories: List<Story>)

    @Delete
    fun delete(user: Story)
}