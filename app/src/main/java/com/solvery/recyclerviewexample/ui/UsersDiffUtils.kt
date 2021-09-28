package com.solvery.recyclerviewexample.ui

import androidx.recyclerview.widget.DiffUtil
import com.solvery.recyclerviewexample.data.domain.models.Story

class UsersDiffUtils(
    private val oldList: List<Story>,
    private val newList: List<Story>
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