package com.jcoding.gtodoapp.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jcoding.gtodoapp.logic.TaskViewModel
import com.jcoding.gtodoapp.databinding.FragmentAddTaskSheetBinding
import com.jcoding.gtodoapp.data.Task
import java.time.LocalDate
import java.time.LocalTime


class AddTaskSheet(
    var task: Task?,
    private val recyclerView: RecyclerView
) : BottomSheetDialogFragment() {


    private lateinit var binding: FragmentAddTaskSheetBinding
    private lateinit var taskViewModel: TaskViewModel
    private var dueTime: LocalTime? = null
    private var dueDate: LocalDate? = null
    private val editable = Editable.Factory.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskSheetBinding.inflate(inflater,container,false)
        return binding.root
    }



    @SuppressLint("NewApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val activity = requireActivity()

        if (task != null)
        {
            binding.taskTitle.text = "Edit Task"

            binding.title.text = editable.newEditable(task!!.title)
            binding.note.text = editable.newEditable(task!!.note)

            dueTime = LocalTime.parse(task!!.time)
            dueDate = LocalDate.parse(task!!.date)

        }
        else
        {
            binding.taskTitle.text = "New Task"
            dueTime = LocalTime.now()
            dueDate = LocalDate.now()

        }

        updateDateTime()

        taskViewModel = ViewModelProvider(activity)[TaskViewModel::class.java]


        binding.saveButton.setOnClickListener{
            saveTodo()
        }

        binding.datePicker.setOnClickListener {
            openDatePicker()
        }

        binding.timePicker.setOnClickListener {
            openTimePicker()
        }

        binding.closeDialog.setOnClickListener {
            dismiss()
        }
    }

    @SuppressLint("NewApi")
    private fun openDatePicker() {
        if(dueDate == null)
            dueDate = LocalDate.now()

        val dateListener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            dueDate = LocalDate.of(year,month, day)
            updateDateTime()
        }

        val dateDialog = DatePickerDialog(
            requireActivity(),
            dateListener,
            dueDate!!.year,
            dueDate!!.monthValue,
            dueDate!!.dayOfMonth
        )
        dateDialog.setTitle("Task Due")
        dateDialog.show()
    }




    private fun saveTodo() {
        val title = binding.title.text.toString()
        val note = binding.note.text.toString()

        taskViewModel.addTask(
            Task(
                title=title,
                note=note,
                date=dueDate.toString(),
                time=dueTime.toString()
            )
        )

        val adapter = recyclerView.adapter!!

//        adapter.notifyItemInserted()
//        recyclerView.scrollToPosition(adapter.itemCount)

        println("Done")
        dismiss()
    }




    @SuppressLint("NewApi")
    private fun openTimePicker() {
        if(dueTime == null)
            dueTime = LocalTime.now()
        val listener = TimePickerDialog.OnTimeSetListener{ _, selectedHour, selectedMinute ->
            dueTime = LocalTime.of(selectedHour, selectedMinute)
            updateDateTime()
        }
        val dialog = TimePickerDialog(activity, listener, dueTime!!.hour, dueTime!!.minute, true)
        dialog.setTitle("Task Due")
        dialog.show()
    }


    @SuppressLint("NewApi")
    private fun updateDateTime() {
        val time = String.format("%02d:%02d",dueTime!!.hour,dueTime!!.minute)
        val date = dueDate.toString()

        binding.date.text = editable.newEditable(date)
        binding.time.text = editable.newEditable(time)
    }


    @SuppressLint("NewApi")
    private fun getFormattedDateTimeString(date: String, time: String): String {
        return String.format(
            "%s  %s  ",
            date,
            time
        )
    }


}