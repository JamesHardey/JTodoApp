package com.jcoding.gtodoapp.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jcoding.gtodoapp.databinding.CardItemBinding
import com.jcoding.gtodoapp.data.Task
import com.jcoding.gtodoapp.logic.TaskClickListener
import com.jcoding.gtodoapp.ui.TaskViewHolder

class TaskAdapter(
    private val clickListener: TaskClickListener
): RecyclerView.Adapter<TaskViewHolder>() {

    private var tasks = emptyList<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val taskBinding = CardItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TaskViewHolder(parent.context,taskBinding,clickListener)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.bind(task)

    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }


    fun getTask(position: Int): Task = tasks[position]


}