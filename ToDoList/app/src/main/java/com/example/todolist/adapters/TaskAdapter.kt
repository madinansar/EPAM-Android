package com.example.todolist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(private val context: Context, private val taskList: MutableList<Task>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.cb_task)
        val taskTextView: TextView = itemView.findViewById(R.id.et_task_name)
        val editButton: ImageButton = itemView.findViewById(R.id.btn_edit_task)
        val deleteButton: ImageButton = itemView.findViewById(R.id.btn_delete_task)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.todo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = taskList[position]
        holder.taskTextView.text = task.name
        holder.checkBox.isChecked = task.checked

        // Toggle task completion
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            task.checked = isChecked
        }

        // Handle task deletion
        holder.deleteButton.setOnClickListener {
            taskList.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int = taskList.size
}
