package com.example.tasks

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.tasks.databinding.FragmentEditTaskBinding

class EditTaskFragment : Fragment() {
    private lateinit var binding: FragmentEditTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTaskBinding.inflate(inflater)

        //get args from bundle
        val args = EditTaskFragmentArgs.fromBundle(requireArguments())

        //create view model
        val application = requireNotNull(this.activity).application
        val dao = TaskDatabase.getInstance(application).taskDao

        val viewModelFactory = EditTaskViewModelFactory(args.taskId, dao)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(EditTaskViewModel::class.java)

        //set onclickListeners
        binding.editTaskBtUpdate.setOnClickListener {
            viewModel.updateTask(binding.editTaskEtTaskName.text.toString(), binding.editTaskCbDone.isChecked)
        }
        binding.editTaskBtDelete.setOnClickListener { viewModel.deleteTask() }

        //set observers
        viewModel.navigateToList.observe(viewLifecycleOwner, Observer { navigate ->
            if(navigate) {
                view?.findNavController()
                    ?.navigate(R.id.action_editTaskFragment_to_tasksFragment)
                viewModel.onNavigatedToList()
            }
        })

        viewModel.task.observe(viewLifecycleOwner, Observer { task ->
            //set views
            binding.editTaskEtTaskName.setText(viewModel.task.value!!.taskName)
            binding.editTaskCbDone.isChecked = viewModel.task.value!!.taskDone
        })

        // Inflate the layout for this fragment
        return binding.root
    }
}