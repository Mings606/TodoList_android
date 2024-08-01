package com.todo.list.home.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.todo.list.data.TaskDatabase
import com.todo.list.data.TaskRepository
import com.todo.list.data.TaskTable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<TaskTable>>
    private val repository: TaskRepository

    init {
        val taskDao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(taskDao)
        readAllData = repository.allTasks
    }

    fun insert(task: TaskTable) {
        viewModelScope.launch(Dispatchers.IO) { repository.insert(task) }
    }

    fun update(task: TaskTable) {
        viewModelScope.launch(Dispatchers.IO) { repository.update(task) }
    }

    fun delete(task: TaskTable) {
        viewModelScope.launch(Dispatchers.IO) { repository.delete(task) }
    }
}

