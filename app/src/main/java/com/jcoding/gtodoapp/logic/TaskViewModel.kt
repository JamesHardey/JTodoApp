package com.jcoding.gtodoapp.logic

import androidx.lifecycle.*
import com.jcoding.gtodoapp.data.Task
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository): ViewModel() {

    private val _tasks:MutableLiveData<List<Task>> =
        repository.getAllTask().asLiveData() as MutableLiveData<List<Task>>

    val tasks: LiveData<List<Task>>
        get() = _tasks

    val filteredData: MediatorLiveData<List<Task>> = MediatorLiveData<List<Task>>()


    init{
        print("viw model  ${_tasks.value}")
        filteredData.addSource(_tasks){
            filteredData.value = it
            println("Filtered $it")
        }
    }


    fun filterData(query: String) {
        if(_tasks.value != null){
            filteredData.value = _tasks.value!!.filter { it.title.contains(query,true) }
        }
//        filteredData.value = _tasks.value!!.filter { it.title.contains(query,true) }
    }



    fun setCompleted(task: Task) {
        task.isCompleted = !task.isCompleted
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }


    fun addTask(task: Task){
        viewModelScope.launch {
            repository.insertTask(task)
        }
    }


    fun deleteTask(task: Task){
        viewModelScope.launch {
            repository.deleteTask(task)
        }
    }

    fun updateTaskItem(task: Task) = viewModelScope.launch {
        repository.updateTask(task)
    }

/*
    fun filterListFor(key: String): LiveData<List<Task>> =
            repository.getFilteredList(key).asLiveData()*/

}

class TaskModelFactory(private val repository: TaskRepository) : ViewModelProvider.Factory
{
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T
    {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java))
            return TaskViewModel(repository) as T

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}