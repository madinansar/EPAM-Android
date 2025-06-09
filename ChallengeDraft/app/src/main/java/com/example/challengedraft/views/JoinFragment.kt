package com.example.challengedraft.views

import com.example.challengedraft.data.models.ChallengeEntity
import com.google.firebase.firestore.FirebaseFirestore

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.challengedraft.viewmodels.ChallengeViewModel
import com.example.challengedraft.R
import com.example.challengedraft.applyColorToButtons
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JoinFragment : Fragment(R.layout.fragment_join) {

    private val challengeViewModel: ChallengeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val codeEditText = view.findViewById<EditText>(R.id.code_edittext)
        val joinButton = view.findViewById<Button>(R.id.join_button)

        applyColorToButtons(requireContext(), joinButton)

        joinButton.setOnClickListener {
            val enteredCode = codeEditText.text.toString().trim()
            if (enteredCode.isNotBlank()) {
                challengeViewModel.joinChallenge(
                    roomCode = enteredCode,
                    onSuccess = { challenge ->
                        challengeViewModel.insertChallengeLocal(challenge)
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(requireContext(), "Joined Challenge: ${challenge.title}", Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_joinFragment_to_challengesFragment)
                        }
                    },
                    onFailure = {
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(requireContext(), "Challenge not found", Toast.LENGTH_SHORT).show()
                        }
                    }
                )
            } else {
                Toast.makeText(requireContext(), "Enter a code", Toast.LENGTH_SHORT).show()
            }
        }
    }
}


