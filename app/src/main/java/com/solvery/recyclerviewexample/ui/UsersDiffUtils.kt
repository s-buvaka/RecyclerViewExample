package com.solvery.recyclerviewexample.ui

import androidx.recyclerview.widget.DiffUtil
import com.solvery.recyclerviewexample.ui.models.StoryVO

class UsersDiffUtils(
    private val oldList: List<StoryVO>,
    private val newList: List<StoryVO>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition].url == oldList[oldItemPosition].url
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition] == oldList[oldItemPosition]
    }
}