package com.example.todoapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

enum class Priority {
    LOW, DEFAULT, IMMEDIATE
}

// TODO Date type?

@Parcelize
data class TodoItem(
    val id: String,
    val text: String,
    val priority: Priority,
    val deadline: Date? = null,
    val isDone: Boolean,
    val creationDate: Date,
    val changeDate: Date? = null
): Parcelable