package com.solvery.recyclerviewexample.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.solvery.recyclerviewexample.databinding.ListItemSectionBinding
import com.solvery.recyclerviewexample.databinding.ListItemStoryBinding
import com.solvery.recyclerviewexample.ui.models.SectionVO
import com.solvery.recyclerviewexample.ui.models.StoryVO
import com.solvery.recyclerviewexample.ui.models.VisualObject

class StoriesAdapter(
    private val clickListener: (StoryVO) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<VisualObject> = emptyList()

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is StoryVO -> VIEW_TYPE_STORY
            is SectionVO -> VIEW_TYPE_SECTION
            else -> super.getItemViewType(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_STORY -> {
                val binding = ListItemStoryBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                StoriesViewHolder(binding, clickListener)
            }
            VIEW_TYPE_SECTION -> {
                val binding = ListItemSectionBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false)
                SectionViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Wrong View Type for this Adapter")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is StoryVO -> (holder as StoriesViewHolder).bind(item)
            is SectionVO -> (holder as SectionViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun update(items: List<VisualObject>) {

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

    class StoriesViewHolder(
        private val binding: ListItemStoryBinding,
        private val clickListener: (StoryVO) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(story: StoryVO) {
            with(binding) {
                root.setOnClickListener { clickListener(story) }

                titleField.text = story.title
                bylineField.text = story.byline
                sectionField.text = story.section
            }
        }
    }

    class SectionViewHolder(
        private val binding: ListItemSectionBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(section: SectionVO) {
            with(binding) {
                sectionTitle.text = section.name
            }
        }
    }

    companion object {

        private const val VIEW_TYPE_STORY = 1
        private const val VIEW_TYPE_SECTION = 2
    }
}