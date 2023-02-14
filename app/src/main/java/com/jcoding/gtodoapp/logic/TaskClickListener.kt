package com.jcoding.gtodoapp.logic

import com.jcoding.gtodoapp.data.Task

interface TaskClickListener {

    fun completeTask(task: Task)

    fun editTask(task: Task)
}