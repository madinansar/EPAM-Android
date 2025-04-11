package com.example.todolistnew


import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistnew.databinding.EditListBinding


class EditListActivity : AppCompatActivity() {
    private lateinit var binding: EditListBinding
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EditListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)
        val theList = DataHolder.selectedList

        val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val completedColorStr = prefs.getString("completed_color", "none")
        val nonCompletedColorStr = prefs.getString("non_completed_color", "none2")

        val completedColor = when (completedColorStr) {
            "green" -> Color.GREEN
            "blue" -> Color.BLUE
            "yellow" -> Color.YELLOW
            else -> Color.BLACK
        }

        val nonCompletedColor = when (nonCompletedColorStr) {
            "brown" -> Color.rgb(165, 42, 42)
            "pink" -> Color.rgb(255, 105, 180)
            "violet" -> Color.rgb(148, 0, 211)
            else -> Color.BLACK
        }

        binding.contactRecyclerView.layoutManager = LinearLayoutManager(this)

        adapter = TaskAdapter(this, theList?.items ?: mutableListOf(), completedColor, nonCompletedColor, dbHelper)
        binding.contactRecyclerView.adapter = adapter
        binding.btnAddTask.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("New Task")

            val input = EditText(this)
            input.hint = "Enter task name"
            input.setPadding(40, 20, 40, 20)
            dialog.setView(input)

            dialog.setPositiveButton("Add") { d, _ ->
                val taskName = input.text.toString()
                if (taskName.isNotEmpty() && theList != null) {
                    dbHelper.insertTask(taskName, theList.id)

                    val updatedTasks = dbHelper.getTasksForList(theList.id)
                    theList.items.clear()
                    theList.items.addAll(updatedTasks)

                    val position = theList.items.size - 1
                    adapter.notifyItemInserted(position)
                }
                d.dismiss()
            }

            dialog.setNegativeButton("Cancel") { d, _ ->
                d.cancel()
            }

            dialog.show()
        }

    }
}
