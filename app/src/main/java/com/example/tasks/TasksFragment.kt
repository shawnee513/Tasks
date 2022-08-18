package com.example.tasks

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.tasks.databinding.FragmentTasksBinding

class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding
    private lateinit var viewModel: TasksViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksBinding.inflate(inflater)

        //build the database if it doesn't already exist and get a reference to the taskDao property
        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao

        //get the view model
        val viewModelFactory = TasksViewModelFactory(dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(TasksViewModel::class.java)

        //set on click listeners
        binding.taskBtSaveTask.setOnClickListener { saveTask() }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun saveTask(){
        viewModel.newTaskName = binding.tasksEtTaskEntry.text.toString()
        viewModel.addTask()
    }
}