package com.example.todoapp

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Date

class TodoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val text: TextView = itemView.findViewById(R.id.todo_text)
    private val isDone: CheckBox = itemView.findViewById(R.id.checkBox)

    fun onBind(todoItem: TodoItem) {
        text.text = todoItem.text
        isDone.isChecked = todoItem.isDone
    }
}