package com.example.notesnew

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.notesnew.databinding.FragmentMainBinding

class MainFragment : Fragment(R.layout.fragment_main) {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapter: NoteAdapter
    private lateinit var binding: FragmentMainBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)
        adapter = NoteAdapter(emptyList()) { note ->
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToDetailsFragment(
                    note.id
                )
            )
        }

        binding.recyclerView.adapter = adapter

        viewModel.allNotes.observe(viewLifecycleOwner) { notes ->
            adapter.updateNotes(notes)
        }
        binding.addNoteButton.setOnClickListener{
            findNavController().navigate(MainFragmentDirections.actionMainFragmentToCreateNoteFragment())
        }

    }
}