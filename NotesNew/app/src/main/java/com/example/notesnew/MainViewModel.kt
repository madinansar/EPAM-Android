package com.example.notesnew

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val noteDao = NoteDatabase.getDatabase(application).noteDao()

    val allNotes: LiveData<List<Note>> = noteDao.getAllNotes()

    fun getNoteById(id: Long): LiveData<Note> {
        return noteDao.getNoteById(id)
    }

    fun insert(note: Note) {
        viewModelScope.launch {
            noteDao.insert(note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.delete(note)
        }
    }

    fun updateNote(note: Note){
        viewModelScope.launch {
            noteDao.update(note)
        }
    }
}
