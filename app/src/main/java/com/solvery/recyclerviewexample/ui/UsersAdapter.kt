package com.solvery.recyclerviewexample.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solvery.recyclerviewexample.R
import com.solvery.recyclerviewexample.data.models.User
import com.solvery.recyclerviewexample.databinding.ListItemUserBinding

class UsersAdapter(
    private val clickListener: (User) -> Unit
) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    private var items: List<User> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun update(items: List<User>) {
        this.items = items
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: ListItemUserBinding,
        private val clickListener: (User) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            with(binding) {
                root.setOnClickListener { clickListener(user) }

                nameField.text = user.name
                surnameField.text = user.surname
                ageField.text =
                    root.context.resources.getQuantityString(
                        R.plurals.age_plurals,
                        user.age,
                        user.age
                    )
            }
        }
    }
}