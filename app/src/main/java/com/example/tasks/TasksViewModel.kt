package com.example.tasks

import android.util.Log
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TasksViewModel (val dao: TaskDao) : ViewModel() {
    var newTaskName = ""

    //we will now make tasks public
    val tasks = dao.getAll()

    /*//get the records from the database and transform them
    //we only used this code before we added a recycler view
    private val tasks = dao.getAll()
    //Transforms the tasks into a LiveData<String>, which is assigned to tasksSttring.
    //Since this is a live data object, it will automatically get updated if the data changes
    val tasksString = Transformations.map(tasks){
        tasks -> formatTasks(tasks)
    }*/


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
        Log.i("test", "inside addTask()")
        viewModelScope.launch{
            Log.i("test", "inside viewmodelscope")
            val task = Task()
            Log.i("test", "created task")
            task.taskName = newTaskName
            Log.i("test", "set task name")
            dao.insert(task)
            Log.i("test", "inserted task and completed function")
        }
    }
    //no longer need these
    /*//formats a list of tasks as a String.
    fun formatTasks(tasks: List<Task>): String {
        return tasks.fold("") {
            str, item -> str + '\n' + formatTask(item)
        }
    }
    //formats each task as a string
    fun formatTask(task: Task): String {
        var str = "ID: ${task.taskId}"
        str += '\n' + "Name: ${task.taskName}"
        str += '\n' + "Complete: ${task.taskDone}" + '\n'
        return str
    }*/
}