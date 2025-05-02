package com.example.notesnew

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notesnew.databinding.FragmentCreateNoteBinding

class CreateNoteFragment : Fragment(R.layout.fragment_create_note) {
    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentCreateNoteBinding

    private lateinit var noteNameText: EditText
    private lateinit var editNoteContent: EditText
    private lateinit var saveNoteButton: Button


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateNoteBinding.bind(view)

        noteNameText = binding.noteNameText
        editNoteContent = binding.editNoteContent
        saveNoteButton = binding.saveNoteButton

        saveNoteButton.setOnClickListener() {
            val noteName = noteNameText.text.toString().trim()
            val noteText = editNoteContent.text.toString().trim()
            val currentTime = System.currentTimeMillis()
            Log.d("CreateNoteFragment", "Save button clicked")

            if (noteName.isEmpty() || noteText.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Please enter both note name and text",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val note = Note(
                name = noteName,
                timestamp = currentTime,
                text = noteText
            )

            viewModel.insert(note)
            Toast.makeText(requireContext(), "Note saved successfully", Toast.LENGTH_SHORT).show()

            noteNameText.setText("")
            editNoteContent.setText("")
            findNavController().navigateUp()

        }
    }

}