package com.jcoding.gtodoapp.ui

import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.jcoding.gtodoapp.*
import com.jcoding.gtodoapp.databinding.ActivityMainBinding
import com.jcoding.gtodoapp.data.Task
import com.jcoding.gtodoapp.logic.TaskClickListener
import com.jcoding.gtodoapp.logic.TaskModelFactory
import com.jcoding.gtodoapp.logic.TaskViewModel
import com.jcoding.gtodoapp.util.TaskAdapter

class MainActivity : AppCompatActivity(), TaskClickListener {

    private lateinit var binding: ActivityMainBinding
    private val taskViewModel: TaskViewModel by viewModels {
        TaskModelFactory((application as TaskApplication).repository)
    }
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var recycler: RecyclerView
    private lateinit var search: SearchView
    private lateinit var preferences: SharedPreferences
    private var nightMode: Boolean = false
    private lateinit var nightModeButton: ImageButton
    private lateinit var fab: FloatingActionButton

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        nightMode = preferences.getBoolean(NIGHT_MODE, false)
        nightModeButton = binding.nightIcon
        fab = binding.addtaskButton
        initializeNightMode()

        fab.setOnClickListener {
            AddTaskSheet(null,recycler).show(supportFragmentManager, "Add Task")
        }

        recycler = binding.recyclerView
        taskAdapter = TaskAdapter(this)
        recycler.adapter = taskAdapter
        setRecyclerOnScroll()

        taskViewModel.tasks.observe(this) {
            println("Task Viewmodel")
            taskAdapter.setTasks(it)
        }

        taskViewModel.filteredData.observe(this) {
            taskAdapter.setTasks(it)
        }


        nightModeButton.setOnClickListener {
            changeNightMode()
        }

        initializeSearch()
        createItemTouchHelper().attachToRecyclerView(recycler)
    }


    private fun initializeNightMode(){
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            nightModeButton.setImageResource(R.drawable.sun)
//            setNight(true)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//            setNight(false)
            nightModeButton.setImageResource(R.drawable.bedtime)
        }
    }




    private fun changeNightMode() {
        val editor = preferences.edit()
        editor.putBoolean(NIGHT_MODE, !nightMode)
        editor.apply()

        val nightMode = preferences.getBoolean(NIGHT_MODE, false)
        if (nightMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            nightModeButton.setImageResource(R.drawable.sun)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            nightModeButton.setImageResource(R.drawable.bedtime)
        }

    }


    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: ViewHolder,
                target: ViewHolder
            ): Boolean {
                return false
            }
            override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val task = taskAdapter.getTask(position)
                taskViewModel.deleteTask(task)
                taskAdapter.notifyItemRemoved(position)
                Toast.makeText(this@MainActivity, "Task Deleted", Toast.LENGTH_SHORT).show()
            }
        }
        return ItemTouchHelper(callback)
    }


    private fun setRecyclerOnScroll(){
        recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) fab.hide() else fab.show()
            }
        })
    }



    override fun completeTask(task: Task) {
        taskViewModel.setCompleted(task)
    }



    override fun editTask(task: Task) {
        AddTaskSheet(task, recycler).show(supportFragmentManager,"newTaskTag")
    }



    private fun initializeSearch(){
        search = binding.imageButton
        search.maxWidth = Int.MAX_VALUE

//        val lifecycle = this

        search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val searchQuery = newText ?: ""

                taskViewModel.filterData(searchQuery)
                return true
            }
        })
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }



    companion object {
        const val NIGHT_MODE = "night_mode"
    }
}