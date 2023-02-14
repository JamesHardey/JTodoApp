package com.jcoding.gtodoapp.logic

import com.jcoding.gtodoapp.data.Task
import com.jcoding.gtodoapp.data.TaskDao

class TaskRepository(private val taskDao: TaskDao) {

    suspend fun insertTask(task: Task) = taskDao.insertTask(task)

    suspend fun updateTask(task: Task) = taskDao.updateTask(task)

    suspend fun deleteTask(task: Task) = taskDao.deleteTask(task)

    suspend fun getFilteredList(key: String) = taskDao.getFilteredList(key)

    fun getAllTask() = taskDao.getAllTasks()

}