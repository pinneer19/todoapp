package com.example.todoapp

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Rect
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.widget.ImageViewCompat
import androidx.fragment.app.Fragment
import com.example.todoapp.databinding.AddItemBinding
import com.example.todoapp.fragments.contract.navigator
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


inline fun <reified T : Parcelable> Bundle.parcelable(key: String): T? = when {
    SDK_INT >= 33 -> getParcelable(key, T::class.java)
    else -> @Suppress("DEPRECATION") getParcelable(key) as? T
}


class AddItemFragment : Fragment(), PopupMenu.OnMenuItemClickListener {

    private lateinit var binding: AddItemBinding
    private var item: TodoItem? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = savedInstanceState?.parcelable(ARG_ITEM_DETAILS)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = AddItemBinding.inflate(inflater, container, false)

        binding.cancelButton.setOnClickListener { onCancelPressed() }
        binding.switchButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onSwitchEnablePressed(requireContext())
            } else {
                onSwitchDisablePressed()
            }
        }
        binding.saveButton.setOnClickListener { onSavePressed() }
        binding.prioritySelector.setOnClickListener { onPrioritySetPressed() }

        binding.editText.setText(item?.text)
        binding.prioritySelector.text = item?.priority?.name
        if (item != null)
            binding.dateField.text = SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.getDefault())
                .format(item!!.deadline!!.time)

        // TODO: change button for delete depending on item
        if (item != null) {
            val color = ContextCompat.getColor(requireContext(), R.color.red)
            ImageViewCompat.setImageTintList(
                binding.buttonIcon,
                ColorStateList.valueOf(color)
            )
            binding.buttonText.setTextColor(color)
        }

        binding.deleteButton.setOnClickListener { onDeletePressed() }
        return binding.root
    }

    private fun onPrioritySetPressed() {
        val popup = PopupMenu(requireContext(), binding.prioritySelector)
        popup.setOnMenuItemClickListener(this)
        popup.inflate(R.menu.priority_menu)
        popup.show()
    }

    private fun onDeletePressed() {
        // TODO: add deleting item logic 
        navigator().goBack()
    }

    private fun onCancelPressed() {
        navigator().goBack()
    }

    private fun getMonth(month: Int): String {
        return DateFormatSymbols().months[month - 1];
    }

    private fun onSwitchDisablePressed() {
        binding.dateField.text = ""
    }

    private fun onSwitchEnablePressed(context: Context) {
        // the instance of our calendar.
        val calendar = Calendar.getInstance()


        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(

            context,
            R.style.DialogTheme,
            { _, year, monthOfYear, dayOfMonth ->

                val date = (dayOfMonth.toString() + " " + getMonth(monthOfYear + 1) + " " + year)
                binding.dateField.text = date

            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.minDate = calendar.timeInMillis

        datePickerDialog.setOnCancelListener {
            binding.switchButton.isChecked = false
            onSwitchDisablePressed()
        }
        datePickerDialog.show()
    }

    private fun onSavePressed() {

        navigator().publishResult(
            item?.copy(
                text = binding.editText.text.toString(),
                priority = when (binding.prioritySelector.text) {
                    "LOW" -> Priority.LOW
                    "DEFAULT" -> Priority.DEFAULT
                    "IMMEDIATE" -> Priority.IMMEDIATE
                    else -> throw Exception("Unknown priority value")
                },
                deadline = SimpleDateFormat(
                    DATE_FORMAT_PATTERN,
                    Locale.getDefault()
                ).parse(binding.dateField.text.toString())
            )!!
        )
        navigator().goBack()
    }

    companion object {

        @JvmStatic
        private val ARG_ITEM_DETAILS = "ARG_ITEM_DETAILS"

        private const val DATE_FORMAT_PATTERN = "MM/dd/yyyy"

        @JvmStatic
        fun newInstance(item: TodoItem): ListFragment {
            val args = Bundle()
            args.putParcelable(ARG_ITEM_DETAILS, item)
            val fragment = ListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onMenuItemClick(item: MenuItem): Boolean {
        binding.prioritySelector.text = item.title

        when (item.itemId) {
            R.id.item1 -> {
                updatePriority(Priority.DEFAULT)
                return true
            }

            R.id.item2 -> {
                updatePriority(Priority.LOW)
                return true
            }

            R.id.item3 -> {
                updatePriority(Priority.IMMEDIATE)
                return true
            }

            else -> return false
        }
    }

    private fun updatePriority(priority: Priority) {
        item = item?.copy(priority = priority)
    }
}