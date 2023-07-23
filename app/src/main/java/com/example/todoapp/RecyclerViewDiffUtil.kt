package com.example.todoapp

import androidx.recyclerview.widget.DiffUtil

class RecyclerViewDiffUtil<T>(
    private val oldItems: List<T>,
    private val newItems: List<T>,
    private val areItemsTheSame : (oldItem: T, newItem: T) -> Boolean = {
        oldItem, newItem -> oldItem == newItem
    },
    private val areContentsTheSame : (oldItem: T, newItem: T) -> Boolean = {
            oldItem, newItem -> oldItem == newItem
    },
    private val getChangePayload : (oldItemPosition: T, newItemPosition: T) -> Any? = {
        _, _ -> null
    }
): DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return areItemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return areContentsTheSame(oldItem, newItem)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val oldItem = oldItems[oldItemPosition]
        val newItem = newItems[newItemPosition]
        return getChangePayload(oldItem, newItem)
    }
}