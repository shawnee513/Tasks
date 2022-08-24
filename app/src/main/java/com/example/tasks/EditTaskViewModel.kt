package com.example.tasks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class EditTaskViewModel(taskId: Long, private val dao: TaskDao) : ViewModel() {
    val task = dao.get(taskId)

    private val _navigateToList = MutableLiveData<Boolean>(false)
    val navigateToList: LiveData<Boolean> get() = _navigateToList


    fun updateTask(taskName: String, isChecked: Boolean) {
        viewModelScope.launch {
            task.value?.taskName = taskName
            task.value?.taskDone = isChecked
            dao.update(task.value!! as Task)
            _navigateToList.value = true
        }
    }

    fun deleteTask() {
        viewModelScope.launch {
            dao.delete(task.value!! as Task)
            _navigateToList.value = true
        }
    }

    fun onNavigatedToList() {
        _navigateToList.value = false
    }
}