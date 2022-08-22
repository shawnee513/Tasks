package com.example.tasks

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.databinding.TaskItemBinding

class TaskItemAdapter : RecyclerView.Adapter<TaskItemAdapter.TaskItemViewHolder>() {
    var data = listOf<Task>()
    set(value) {
        field = value
        //this tells the recycler view that the data has changed
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
    */
    class TaskItemViewHolder(val rootView: CardView) : RecyclerView.ViewHolder(rootView) {
        //get references to the layout's text view and checkbox
        val taskName = rootView.findViewById<TextView>(R.id.task_name)
        val taskDone = rootView.findViewById<CheckBox>(R.id.task_done)

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
            taskName.text = item.taskName
            taskDone.isChecked = item.taskDone
        }
    }
}