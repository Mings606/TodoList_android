package com.todo.list.data

import androidx.lifecycle.LiveData
import com.todo.list.dao.TaskDao

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: LiveData<List<TaskTable>> = taskDao.getAllTasks()

    suspend fun insert(task: TaskTable) {
        taskDao.insertTask(task)
    }

    suspend fun update(task: TaskTable) {
        taskDao.updateTask(task)
    }

    suspend fun delete(task: TaskTable) {
        taskDao.deleteTask(task)
    }
}
