package com.example.notesnew

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.example.notesnew.databinding.FragmentDetailsBinding
import com.example.notesnew.databinding.FragmentMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailsFragment : Fragment(R.layout.fragment_details) {

    private lateinit var binding : FragmentDetailsBinding
    private val viewModel: MainViewModel by viewModels()
    private var noteId: Long = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailsBinding.bind(view)
        noteId = arguments?.let { DetailsFragmentArgs.fromBundle(it).noteId } ?: return

        val titleText = binding.noteTitleText
        val dateText = binding.noteDateText
        val contentText = binding.noteContentText

        viewModel.getNoteById(noteId).observe(viewLifecycleOwner) { note ->
            note?.let {
                titleText.text = it.name

                contentText.text = it.text

                val formattedDate = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault())
                    .format(Date(it.timestamp))
                dateText.text =formattedDate



                binding.deleteNoteButton.setOnClickListener{
                    viewModel.deleteNote(note)
                    findNavController().navigateUp()
                }

                binding.editNoteButton.setOnClickListener(){
                    val action = DetailsFragmentDirections.actionDetailsFragmentToEditFragment(noteId)
                    findNavController().navigate(action)
                }
            }

        }


    }
}