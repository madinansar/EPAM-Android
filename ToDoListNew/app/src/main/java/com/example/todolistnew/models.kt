package com.example.todolistnew

data class Task(
    val id: Int,
    var name: String,
    var checked: Boolean
)

data class TheList(
    val id: Int,
    var name: String,
    val items: MutableList<Task>
)
