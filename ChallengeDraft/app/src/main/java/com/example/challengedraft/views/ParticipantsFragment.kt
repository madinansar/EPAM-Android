package com.example.challengedraft.views

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.challengedraft.viewmodels.ChallengeViewModel
import com.example.challengedraft.viewmodels.ParticipantViewModel
import com.example.challengedraft.R
import com.example.challengedraft.applyColorToButtons
import com.example.challengedraft.data.models.ParticipantEntity
import java.util.UUID
class ParticipantsFragment : Fragment(R.layout.fragment_participants) {
    private lateinit var adapter: ParticipantAdapter
    private val viewModel: ParticipantViewModel by viewModels()
    private val challengeViewModel: ChallengeViewModel by viewModels()
    private val args: ParticipantsFragmentArgs by navArgs() // Get challengeId

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nameEditText = view.findViewById<EditText>(R.id.participant_name_edittext)
        val addButton = view.findViewById<Button>(R.id.add_participant_button)
        val recyclerView = view.findViewById<RecyclerView>(R.id.participantRecyclerView)
        val titleTextView = view.findViewById<TextView>(R.id.challengeId_textview)

        applyColorToButtons(requireContext(), addButton)

        adapter = ParticipantAdapter(viewModel = viewModel)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        val challengeId = args.challengeId

        challengeViewModel.getChallengeById(challengeId).observe(viewLifecycleOwner) { challenge ->
            challenge?.let {
                titleTextView.text = "Challenge: ${it.title} (Code: ${it.id})"
            }
        }
        viewModel.startSync(challengeId)

        viewModel.getParticipantsByChallengeId(args.challengeId).observe(viewLifecycleOwner) { participants ->
            adapter.setParticipants(participants)

        }

        addButton.setOnClickListener {
            val name = nameEditText.text.toString().trim()
            if (name.isNotBlank()) {
                val participant = ParticipantEntity(
                    id = UUID.randomUUID().toString(),
                    challengeId = args.challengeId,
                    name = name,
                    count = 0
                )
                viewModel.insertParticipant(participant)
                nameEditText.text.clear()
            } else {
                Toast.makeText(requireContext(), "Enter a name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
