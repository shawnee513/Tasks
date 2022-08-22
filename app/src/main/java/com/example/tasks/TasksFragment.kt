package com.example.tasks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.tasks.databinding.FragmentTasksBinding

class TasksFragment : Fragment() {
    private lateinit var binding: FragmentTasksBinding
    private lateinit var viewModel: TasksViewModel
    private lateinit var viewModelFactory: TasksViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTasksBinding.inflate(inflater)

        //build the database if it doesn't already exist and get a reference to the taskDao property
        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao

        //get the view model
        viewModelFactory = TasksViewModelFactory(dao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(TasksViewModel::class.java)

        //set on click listeners
        binding.taskBtSaveTask.setOnClickListener { saveTask() }

        /*//Set observers
        viewModel.tasksString.observe(viewLifecycleOwner, Observer {tasks -> binding.taskTvTaskList.text = tasks})
*/
        //connect the adapter to the recycler view
        val adapter = TaskItemAdapter()
        binding.tasksRvTaskList.adapter = adapter

        //observe tasks and give to adapter
        viewModel.tasks.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun saveTask(){
        Log.i("test", "inside saveTask()")
        viewModel.newTaskName = binding.tasksEtTaskEntry.text.toString()
        Log.i("test", "got task name")
        viewModel.addTask()
        Log.i("test", "added task")
    }
}