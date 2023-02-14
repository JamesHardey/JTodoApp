package com.jcoding.gtodoapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "task")
data class Task (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var note: String,
    var date: String,
    var time: String,
    var isCompleted:Boolean = false
)
