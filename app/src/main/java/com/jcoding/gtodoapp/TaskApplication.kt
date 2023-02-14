package com.jcoding.gtodoapp

import android.app.Application
import com.jcoding.gtodoapp.data.TaskDatabase
import com.jcoding.gtodoapp.logic.TaskRepository


class TaskApplication: Application() {
    val database by lazy { TaskDatabase.getInstance(this) }
    val repository by lazy { TaskRepository(database.taskDao()) }
}