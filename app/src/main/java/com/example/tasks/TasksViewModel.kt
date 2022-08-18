package com.example.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TasksViewModel (val dao: TaskDao) : ViewModel() {
    var newTaskName = ""

    /*
    any data access operations must be performed on a background thread so they don't block Android's main thread and hold up the UI
    use coroutines to run data access code in the background
    a coroutine is a lightweight thread that lets you run multiple pieces of code asynchronously
    To use coroutines, do two things:
    1 - mark each of the DAO's data access methods with suspend
    2 - launch the DAO's coroutines in the background. for example:
            viewModelScope.launch {
                ....
                dao.insert(task)
            }
    note* - these changes are needed for all data access methods except for ones that return live data. Those are already
    running on a background thread.
    */
    fun addTask() {
        //launch the coroutine in the same scope as the view model
        viewModelScope.launch{
            val task = Task()
            task.taskName = newTaskName
            dao.insert(task)
        }
    }
}