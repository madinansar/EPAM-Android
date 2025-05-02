package com.example.notesnew

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.notesnew.databinding.FragmentEditBinding

class EditFragment : Fragment(R.layout.fragment_edit) {

    private lateinit var binding : FragmentEditBinding
    private val viewModel: MainViewModel by viewModels()
    private var noteId: Long = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentEditBinding.bind(view)
        noteId = arguments?.let { EditFragmentArgs.fromBundle(it).noteId } ?: return

        val noteName : TextView = binding.noteNameText
        val noteContent : EditText= binding.editNoteContent

        viewModel.getNoteById(noteId).observe(viewLifecycleOwner){ note ->
            note?.let {
                noteName.text = it.name
                noteContent.setText(it.text)

                binding.saveNoteButton.setOnClickListener {
                    val updatedNote = Note(
                        id = noteId,
                        name = note.name,
                        text = binding.editNoteContent.text.toString(),
                        timestamp = System.currentTimeMillis()
                    )
                    viewModel.updateNote(updatedNote)
                    findNavController().navigateUp()
                }


            }
        }
    }

}