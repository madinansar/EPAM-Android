package com.example.todolistnew

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistnew.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ListAdapter
    private lateinit var dbHelper: DatabaseHelper
    private val lists = mutableListOf<TheList>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper(this)
        lists.clear()
        lists.addAll(dbHelper.getAllLists())

        adapter = ListAdapter(this, lists, dbHelper)
        binding.listOfLists.layoutManager = LinearLayoutManager(this)
        binding.listOfLists.adapter = adapter

        binding.btnAddList.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            dialog.setTitle("New List")

            val input = EditText(this)
            input.hint = "Enter list name"
            input.inputType = InputType.TYPE_CLASS_TEXT
            input.setPadding(40, 20, 40, 20)

            dialog.setView(input)

            dialog.setPositiveButton("Add") { d, _ ->
                val listName = input.text.toString().trim()
                if (listName.isNotEmpty()) {
                    val newListId = dbHelper.insertList(listName)
                    if (newListId != -1L) {
                        val newList = TheList(newListId.toInt(), listName, mutableListOf())
                        lists.add(newList)
                        adapter.notifyItemInserted(lists.size - 1)
                    }
                }
                d.dismiss()
            }
            dialog.setNegativeButton("Cancel") { d, _ ->
                d.cancel()
            }
            dialog.create().show()
        }

        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

}
