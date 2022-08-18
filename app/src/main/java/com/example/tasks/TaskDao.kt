package com.example.tasks

import androidx.lifecycle.LiveData
import androidx.room.*

//This will define the methods which can access the data in the table
@Dao
interface TaskDao {
    //adding suspend turns the method into a coroutine so it runs in the background
    @Insert
    suspend fun insert(task: Task)

    @Update
    suspend fun update(task: Task)

    @Delete
    suspend fun delete(task: Task)

    //@Query for everything else
    //we won't add suspend to get() or getAll() because they use live data
    @Query("SELECT * FROM task_table WHERE taskId = :taskId")
    fun get(taskId: Long): LiveData<Task>

    @Query("SELECT * FROM task_table ORDER BY taskId DESC")
    fun getAll(): LiveData<List<Task>>
}