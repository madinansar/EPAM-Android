package com.example.todolistnew

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "todo.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_LIST = "list_table"
        const val TABLE_TASK = "task_table"

        const val COLUMN_LIST_ID = "id"
        const val COLUMN_LIST_NAME = "name"

        const val COLUMN_TASK_ID = "id"
        const val COLUMN_TASK_LIST_ID = "list_id"
        const val COLUMN_TASK_NAME = "name"
        const val COLUMN_TASK_CHECKED = "checked"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createListTable = """
            CREATE TABLE $TABLE_LIST (
                $COLUMN_LIST_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_LIST_NAME TEXT
            )
        """
        db?.execSQL(createListTable)

        val createTaskTable = """
            CREATE TABLE $TABLE_TASK (
                $COLUMN_TASK_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TASK_LIST_ID INTEGER,
                $COLUMN_TASK_NAME TEXT,
                $COLUMN_TASK_CHECKED INTEGER,
                FOREIGN KEY($COLUMN_TASK_LIST_ID) REFERENCES $TABLE_LIST($COLUMN_LIST_ID)
            )
        """
        db?.execSQL(createTaskTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_TASK")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_LIST")
        onCreate(db)
    }

//CRUD task table
    fun insertTask(taskName: String, listId: Int): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TASK_NAME, taskName)
            put(COLUMN_TASK_LIST_ID, listId)
            put(COLUMN_TASK_CHECKED, 0)
        }
        val id = db.insert(TABLE_TASK, null, values)
        db.close()
        return id
    }

    fun updateTask(taskId: Int, newName: String? = null, isChecked: Boolean = false) {
        val db = writableDatabase
        val values = ContentValues()

        if (newName != null) {
            values.put(COLUMN_TASK_NAME, newName)
        }
        values.put(COLUMN_TASK_CHECKED, if (isChecked) 1 else 0)

        db.update(
            TABLE_TASK,
            values,
            "$COLUMN_TASK_ID = ?",
            arrayOf(taskId.toString())
        )
        db.close()
    }

    fun deleteTask(taskId: Int): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        val result = db.delete(
            TABLE_TASK,
            "$COLUMN_TASK_ID = ?",
            arrayOf(taskId.toString())
        )
        db.close()
        return result > 0
    }

//CRUD list
    fun insertList(name: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_LIST_NAME, name)
        }
        val result = db.insert(TABLE_LIST, null, values)
        db.close()
        return result
    }


    fun getAllLists(): List<TheList> {
        val lists = mutableListOf<TheList>()
        val db = readableDatabase
        val cursor = db.query(TABLE_LIST, null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val listId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_LIST_ID))
            val listName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LIST_NAME))
            val tasks = getTasksForList(listId)

            lists.add(TheList(listId, listName, tasks.toMutableList()))
        }
        cursor.close()
        db.close()
        return lists
    }

    fun updateList(listId: Int, newName: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_LIST_NAME, newName)
        }
        val result = db.update(
            TABLE_LIST,
            values,
            "$COLUMN_LIST_ID = ?",
            arrayOf(listId.toString())
        )
        db.close()
        return result > 0
    }

    fun deleteList(listId: Int): Boolean {
        val db = writableDatabase
        val values = ContentValues()
        db.delete(
            TABLE_TASK,
            "$COLUMN_TASK_LIST_ID = ?",
            arrayOf(listId.toString())
        )
        val result = db.delete(
            TABLE_LIST,
            "$COLUMN_LIST_ID = ?",
            arrayOf(listId.toString())
        )
        db.close()
        return result > 0
    }


    fun getTasksForList(listId: Int): List<Task> {
        val tasks = mutableListOf<Task>()
        val db = readableDatabase
        val cursor = db.query(
            TABLE_TASK, null,
            "$COLUMN_TASK_LIST_ID = ?", arrayOf(listId.toString()),
            null, null, null
        )

        while (cursor.moveToNext()) {
            val taskId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TASK_ID))
            val taskName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASK_NAME))
            val isChecked = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TASK_CHECKED)) == 1
            tasks.add(Task(taskId, taskName, isChecked))
        }
        cursor.close()
        db.close()
        return tasks
    }
}
