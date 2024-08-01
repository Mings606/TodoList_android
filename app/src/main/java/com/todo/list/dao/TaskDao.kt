package com.todo.list.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.todo.list.data.TaskTable

@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskTable)

    @Update
    suspend fun updateTask(task: TaskTable)

    @Delete
    suspend fun deleteTask(task: TaskTable)

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): LiveData<List<TaskTable>>
}
