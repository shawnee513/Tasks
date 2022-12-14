package com.example.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.databinding.TaskItemBinding

//the way of doing it using DiffUtil
//to make items clickable, taskItemAdapter will accept a clickListener from the fragment
//and will pass it to onBindViewHolder
//this makes the fragment responsible for what happens when the item is clicked
class TaskItemAdapter(val clickListener: (taskId: Long) -> Unit) : ListAdapter<Task, TaskItemAdapter.TaskItemViewHolder>(TaskDiffItemCallback()){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
        : TaskItemViewHolder = TaskItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class TaskItemViewHolder(val rootView: CardView) : RecyclerView.ViewHolder(rootView) {
        //set binding so we can reference views
        private val binding = TaskItemBinding.bind(rootView)

        //creating this inside a companion object means that it can be called without first creating a TaskItemViewHolder object
        companion object {
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.task_item, parent, false) as CardView
                //The method inflates the item's layout, and uses it to create a TaskItemViewHolder
                return TaskItemViewHolder(view)
            }
        }

        fun bind(item: Task, clickListener: (taskId: Long) -> Unit) {
            //add the task name to the layout's root view (a text view)
            binding.taskName.text = item.taskName
            binding.taskDone.isChecked = item.taskDone

            //make the item resond to clicks
            binding.root.setOnClickListener { clickListener(item.taskId) }
        }
    }
}

//the way of doing it before using DiffUtil
/*
class TaskItemAdapter : RecyclerView.Adapter<TaskItemAdapter.TaskItemViewHolder>() {
    var data = listOf<Task>()
    set(value) {
        field = value
        //this tells the recycler view that the data has changed
        //although, it is inefficient, especially for large data sets, because recycler view will
        //redraw all of its views anytime the data changes
        //it is more efficient to use DiffUtil - which compares the old dataset with the new data
        //set and tells the recycler view of what to change
        notifyDataSetChanged()
    }

    //say how many items there are
    override fun getItemCount() = data.size

    //specify which layout to use
    //parent: ViewGroup refers to the recycler view. viewType: Int allows you to use different layouts for different items
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                    : TaskItemViewHolder = TaskItemViewHolder.inflateFrom(parent)


    //specify how to display the task records in the view holder's layout
    //holder is the view holder the method needs to add data to
    //position is the item's position in the data
    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        //get the item that needs to be displayed
        val item = data[position]
        //call the view holder's bind() method for that item
        holder.bind(item)
    }

    //set the view holder
    //view holders are usually tied to a single adapter, so we are defining it as an inner class, but it doesn't have to be
    */
/*
    This was when the root view was a textview. we updated it to use a cardview instead
    class TaskItemViewHolder(val rootView: TextView) : RecyclerView.ViewHolder(rootView) {
    //creating this inside a companion object means that it can be called without first creating a TaskItemViewHolder object
        companion object {
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.task_item, parent, false) as TextView
                //The method inflates the item's layout, and uses it to create a TaskItemViewHolder
                return TaskItemViewHolder(view)
            }
        }

        fun bind(item: Task) {
            //add the task name to the layout's root view (a text view)
            rootView.text = item.taskName
        }
    *//*

    class TaskItemViewHolder(val rootView: CardView) : RecyclerView.ViewHolder(rootView) {
        //set binding so we can reference views
        private val binding = TaskItemBinding.bind(rootView)

        //creating this inside a companion object means that it can be called without first creating a TaskItemViewHolder object
        companion object {
            fun inflateFrom(parent: ViewGroup): TaskItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.task_item, parent, false) as CardView
                //The method inflates the item's layout, and uses it to create a TaskItemViewHolder
                return TaskItemViewHolder(view)
            }
        }

        fun bind(item: Task) {
            //add the task name to the layout's root view (a text view)
            binding.taskName.text = item.taskName
            binding.taskDone.isChecked = item.taskDone
        }
    }
}*/
