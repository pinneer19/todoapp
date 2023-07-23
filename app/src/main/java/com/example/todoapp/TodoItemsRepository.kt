package com.example.todoapp

import java.util.Date

class TodoItemsRepository(

) {

    private val todoItems: MutableList<TodoItem> = mutableListOf()

    init {

        val currentDate = Date()

        // Add 15 instances of TodoItem with different combinations of parameters
        todoItems.add(
            TodoItem(
                "1",
                "Finish project",
                Priority.IMMEDIATE,
                Date(currentDate.time + 86400000),
                false,
                currentDate,
                Date(currentDate.time + 3600000)
            )
        )

        todoItems.add(
            TodoItem(
                "2",
                "Buy groceries",
                Priority.DEFAULT,
                null,
                true,
                currentDate,
                Date(currentDate.time + 7200000)
            )
        )

        todoItems.add(
            TodoItem(
                "3",
                "Go for a run",
                Priority.LOW,
                Date(currentDate.time + 172800000),
                false,
                currentDate,
                null
            )
        )

        todoItems.add(
            TodoItem(
                "4",
                "Attend meeting",
                Priority.IMMEDIATE,
                Date(currentDate.time + 3600000),
                true,
                currentDate,
                null
            )
        )

        todoItems.add(
            TodoItem(
                "5",
                "Read a book",
                Priority.LOW,
                null,
                false,
                currentDate,
                Date(currentDate.time + 7200000)
            )
        )

        todoItems.add(
            TodoItem(
                "6",
                "Pay bills",
                Priority.DEFAULT,
                Date(currentDate.time + 86400000),
                true,
                currentDate,
                Date(currentDate.time + 1800000)
            )
        )

        todoItems.add(
            TodoItem(
                "7",
                "Clean the house",
                Priority.IMMEDIATE,
                null,
                false,
                currentDate,
                Date(currentDate.time + 7200000)
            )
        )

        todoItems.add(
            TodoItem(
                "8",
                "Call a friend",
                Priority.DEFAULT,
                Date(currentDate.time + 3600000),
                true,
                currentDate,
                Date(currentDate.time + 7200000)
            )
        )

        todoItems.add(
            TodoItem(
                "9",
                "Go to the gym",
                Priority.LOW,
                null,
                false,
                currentDate,
                Date(currentDate.time + 3600000)
            )
        )

        todoItems.add(
            TodoItem(
                "10",
                "Finish report",
                Priority.IMMEDIATE,
                Date(currentDate.time + 7200000),
                true,
                currentDate,
                Date(currentDate.time + 1800000)
            )
        )

        todoItems.add(
            TodoItem(
                "11",
                "Walk the dog",
                Priority.DEFAULT,
                null,
                false,
                currentDate,
                Date(currentDate.time + 3600000)
            )
        )

        todoItems.add(
            TodoItem(
                "12",
                "Study for exam",
                Priority.LOW,
                Date(currentDate.time + 86400000),
                true,
                currentDate,
                Date(currentDate.time + 7200000)
            )
        )

        todoItems.add(
            TodoItem(
                "13",
                "Do laundry",
                Priority.IMMEDIATE,
                null,
                false,
                currentDate,
                Date(currentDate.time + 1800000)
            )
        )

        todoItems.add(
            TodoItem(
                "14",
                "Plan vacation",
                Priority.DEFAULT,
                Date(currentDate.time + 172800000),
                true,
                currentDate,
                null
            )
        )

        todoItems.add(
            TodoItem(
                "15",
                "Write a blog post",
                Priority.LOW,
                null,
                false,
                currentDate,
                Date(currentDate.time + 3600000)
            )
        )
    }

    fun getTodoItems() : List<TodoItem> = todoItems

    fun addTodoItem(todoItem: TodoItem) {
        todoItems.add(todoItem)
    }

    fun restoreTodoItem(pos: Int, todoItem: TodoItem) {
        todoItems.add(pos, todoItem)
    }

    fun deleteTodoItem(pos: Int) {
        todoItems.removeAt(pos)
    }

    fun getTodoItem(pos: Int): TodoItem {
        return todoItems[pos]
    }
}