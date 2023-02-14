package com.jcoding.gtodoapp.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.jcoding.gtodoapp.data.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("SELECT * FROM task ORDER BY id ASC")
    fun getAllTasks(): Flow<List<Task>>

    @Query("SELECT * FROM task WHERE title LIKE :key ORDER BY id ASC")
    suspend fun getFilteredList(key: String): List<Task>

}