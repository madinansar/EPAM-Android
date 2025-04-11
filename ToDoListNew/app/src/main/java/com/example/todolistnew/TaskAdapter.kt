package com.example.todolistnew

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistnew.databinding.TaskItemBinding

class TaskAdapter(
    private val context: Context,
    private val tasks: MutableList<Task>,
    private val completedColor: Int,
    private val nonCompletedColor: Int,
    private val dbHelper: DatabaseHelper
) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(binding: TaskItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val cbTask = binding.cbTask
        val tvTaskName = binding.etTaskName
        val btnEdit = binding.btnEditTask
        val btnDelete = binding.btnDeleteTask
    }

    override fun getItemCount() = tasks.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = TaskItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.cbTask.setOnCheckedChangeListener(null)
        holder.cbTask.isChecked = task.checked
        holder.tvTaskName.text = task.name

        holder.tvTaskName.setTextColor(
            if (task.checked) {
                completedColor
            } else {
                nonCompletedColor
            }
        )

        holder.cbTask.setOnCheckedChangeListener { _, isChecked ->
            task.checked = isChecked
            dbHelper.updateTask(task.id, isChecked = isChecked)

            holder.tvTaskName.setTextColor(
                if (isChecked) {
                    completedColor
                } else {
                    nonCompletedColor
                }
            )
        }

        holder.btnEdit.setOnClickListener {
            showUpdateTaskDialog(task)
        }

        holder.btnDelete.setOnClickListener {
            val pos = holder.bindingAdapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                val deleted = dbHelper.deleteTask(tasks[pos].id)
                if (deleted) {
                    tasks.removeAt(pos)
                    notifyItemRemoved(pos)  //move all elements
                }
            }
        }
    }

    private fun showUpdateTaskDialog(task: Task) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle("Update Task")

        val edittext = EditText(context)
        edittext.hint = "Enter task name"
        edittext.setText(task.name)
        edittext.inputType = InputType.TYPE_CLASS_TEXT
        edittext.setPadding(40, 20, 40, 20)

        dialog.setView(edittext)

        dialog.setPositiveButton("Update") { d, _ ->
            val updatedTaskName = edittext.text.toString()
            if (updatedTaskName.isNotEmpty()) {
                task.name = updatedTaskName
                dbHelper.updateTask(task.id, updatedTaskName, task.checked)
                notifyItemChanged(tasks.indexOf(task))
            }
            d.dismiss()
        }
        dialog.setNegativeButton("Cancel") { d, _ ->
            d.cancel()
        }
        dialog.create().show()
    }

}