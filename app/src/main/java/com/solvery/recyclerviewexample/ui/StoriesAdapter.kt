package com.solvery.recyclerviewexample.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.solvery.recyclerviewexample.data.domain.models.Story
import com.solvery.recyclerviewexample.databinding.ListItemStoryBinding

class StoriesAdapter(
    private val clickListener: (Story) -> Unit
) : RecyclerView.Adapter<StoriesAdapter.ViewHolder>() {

    private var items: List<Story> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemStoryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun update(items: List<Story>) {

        if (this.items.isEmpty()) {
            this.items = items
            notifyDataSetChanged()
        } else {
            val callback = UsersDiffUtils(
                oldList = this.items,
                newList = items
            )

            this.items = items
            DiffUtil.calculateDiff(callback).dispatchUpdatesTo(this)
        }
    }

    class ViewHolder(
        private val binding: ListItemStoryBinding,
        private val clickListener: (Story) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(story: Story) {
            with(binding) {
                root.setOnClickListener { clickListener(story) }

                titleField.text = story.title
                bylineField.text = story.byline
                sectionField.text = story.section
            }
        }
    }
}