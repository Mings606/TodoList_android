package com.todo.list.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
data class TaskTable(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val taskName: String,
    var isCompleted: Boolean = false
)
