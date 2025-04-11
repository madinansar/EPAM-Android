package com.example.todolist.models

import java.io.Serializable

data class Task(
    val name: String,
    var checked: Boolean
) : Serializable

