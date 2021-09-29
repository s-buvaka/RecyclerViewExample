package com.solvery.recyclerviewexample.ui.stories

import androidx.recyclerview.widget.DiffUtil
import com.solvery.recyclerviewexample.ui.models.VisualObject

class StoriesDiffUtils(
    private val oldList: List<VisualObject>,
    private val newList: List<VisualObject>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition].id == oldList[oldItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return newList[newItemPosition] == oldList[oldItemPosition]
    }
}