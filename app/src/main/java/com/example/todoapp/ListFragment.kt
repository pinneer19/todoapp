package com.example.todoapp

import android.R
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.databinding.FragmentListBinding
import com.example.todoapp.fragments.contract.navigator
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private lateinit var todoItemsRecyclerView: RecyclerView
    private lateinit var adapter: TodoListAdapter
    private var deletedItem: TodoItem? = null
    private var visible: Boolean = false
    private val todoItemsRepository = TodoItemsRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentListBinding.inflate(inflater, container, false)

        binding.addItemButton.setOnClickListener { onAddItemPressed() }
        binding.visibilityIcon.setOnClickListener { onVisibilityPressed() }

        setupRecyclerView()
        val helper = ItemTouchHelper(object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.END or ItemTouchHelper.START) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                when (direction) {
                    ItemTouchHelper.END -> {

                    }

                    ItemTouchHelper.START -> {
                        deletedItem = todoItemsRepository.getTodoItem(position)
                        todoItemsRepository.deleteTodoItem(position)
                        adapter.notifyItemRemoved(position)
                        Snackbar.make(
                            todoItemsRecyclerView,
                            deletedItem!!.text + ' ' + resources.getString(R.string.deleted_item),
                            Snackbar.LENGTH_LONG
                        ).setAction(
                            resources.getString(R.string.undo)
                        ) {
                            todoItemsRepository.restoreTodoItem(position, deletedItem!!)
                            adapter.notifyItemInserted(position)
                        }.show()
                    }
                }
            }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                RecyclerViewSwipeDecorator.Builder(
                    c,recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive
                )
                    .addBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.red
                        )
                    )
                    .addActionIcon(R.drawable.delete_icon)
                    .create()
                    .decorate()

                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }

        })
        helper.attachToRecyclerView(todoItemsRecyclerView)
        return binding.root
    }

    private fun setupRecyclerView() {
        todoItemsRecyclerView = binding.todoItems
        adapter = TodoListAdapter()
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        todoItemsRecyclerView.adapter = adapter
        todoItemsRecyclerView.layoutManager = layoutManager
        adapter.todoItems = todoItemsRepository.getTodoItems()
    }

    private fun onVisibilityPressed() {
        when (visible) {
            false -> {
                visible = true
                binding.visibilityIcon.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.visibility_off_eye,
                        null
                    )
                )
            }

            true -> {
                visible = false
                binding.visibilityIcon.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        R.drawable.visible_eye,
                        null
                    )
                )
            }
        }

    }

    private fun onAddItemPressed() {
        navigator().showAddItemScreen()
    }

    private fun getItems() = requireArguments()

    companion object {

        @JvmStatic
        private val ARG_ITEMS = "ARG_ITEMS"

        @JvmStatic
        private val ARG_VISIBLE = "ARG_VISIBLE"

        @JvmStatic
        fun newInstance(isVisible: Boolean): ListFragment {
            val args = bundleOf(ARG_VISIBLE to isVisible)
            val fragment = ListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}