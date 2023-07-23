package com.example.todoapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.zip.Inflater

class TodoListAdapter : RecyclerView.Adapter<TodoListViewHolder>() {

    var todoItems = emptyList<TodoItem>()
        set(value) {
            val callback = RecyclerViewDiffUtil(
                oldItems = field,
                newItems = value,
                areItemsTheSame = { oldItem: TodoItem, newItem ->
                    oldItem.id == newItem.id
                },
                getChangePayload = { _, _ -> Any()}
            )
            field = value
            val diffResult = DiffUtil.calculateDiff(callback)
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        return TodoListViewHolder(
            layoutInflater.inflate(
                R.layout.item_template,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = todoItems.size

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.onBind(todoItems[position])
    }


}