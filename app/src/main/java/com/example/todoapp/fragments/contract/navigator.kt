package com.example.todoapp.fragments.contract

import android.os.Parcelable

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.example.todoapp.TodoItem

typealias ResultListener<T> = (T) -> Unit

fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {
    fun showAddItemScreen()

    fun showAddItemScreenWithDetails(item: TodoItem)

    fun goBack()

    fun goToMenu()

    fun <T: Parcelable> publishResult(result: T)

    fun <T: Parcelable> listenResult(clazz: Class<T>, owner: LifecycleOwner, listener: ResultListener<T>)
}