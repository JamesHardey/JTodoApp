package com.jcoding.gtodoapp.ui

import android.annotation.SuppressLint
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.jcoding.gtodoapp.R
import com.jcoding.gtodoapp.logic.TaskClickListener
import com.jcoding.gtodoapp.databinding.CardItemBinding
import com.jcoding.gtodoapp.data.Task

class TaskViewHolder(
    private val context: Context,
    private val binding: CardItemBinding,
    private val clickListener: TaskClickListener
    )
    : RecyclerView.ViewHolder(binding.root) {

        private val title = binding.todoTitle
        private val note = binding.todoNote
        private val date = binding.todoDate
        private val checked = binding.checkBox
        private val card = binding.cardBack

        @SuppressLint("ResourceAsColor")
        fun bind(task: Task){

            title.text = task.title
            note.text = task.note
            date.text = "${task.date}--${task.time}"

            if(task.isCompleted){
                checked.setImageResource(R.drawable.checked)
                card.setBackgroundResource(R.color.yellow)

            }
            else{
                checked.setImageResource(R.drawable.unchecked)
//                card.setBackgroundColor(R.color.white)
                card.setBackgroundResource(R.color.white)
            }

            binding.checkBox.setOnClickListener {
                clickListener.completeTask(task)
            }

            this.itemView.setOnClickListener {
                clickListener.editTask(task)
            }
        }
}