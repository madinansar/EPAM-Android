package com.example.challengedraft.views

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challengedraft.viewmodels.ChallengeViewModel
import com.example.challengedraft.R
import com.example.challengedraft.applyColorToButtons
import com.example.challengedraft.data.models.ChallengeEntity

class ChallengesFragment : Fragment(R.layout.fragment_challenges) {
    private lateinit var adapter: ChallengeAdapter
    private val viewModel: ChallengeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.challengeRecyclerView)
        val addButton = view.findViewById<Button>(R.id.add_challenge_button)
        val titleEditText = view.findViewById<EditText>(R.id.title_edittext)

        applyColorToButtons(requireContext(), addButton)

        adapter = ChallengeAdapter { challengeId ->
            val action = ChallengesFragmentDirections.actionChallengesFragmentToParticipantsFragment(challengeId)
            findNavController().navigate(action)
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        viewModel.allChallenges.observe(viewLifecycleOwner) { challenges ->
            adapter.setChallenges(challenges)
        }


        addButton.setOnClickListener {
            val title = titleEditText.text.toString()
            if (title.isNotBlank()) {
                val challenge = ChallengeEntity(id = generateChallengeCode(), title = title)
                viewModel.insertChallenge(challenge)
                titleEditText.text.clear()
            } else {
                Toast.makeText(requireContext(), "Please enter challenge title", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun generateChallengeCode(): String {
        return (100000..999999).random().toString()
    }

}
