package com.example.challengedraft.views

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.example.challengedraft.viewmodels.ParticipantViewModel
import com.example.challengedraft.R
import com.example.challengedraft.applyColorToButtons
import com.example.challengedraft.data.models.ParticipantEntity

class ParticipantAdapter(
    private val viewModel: ParticipantViewModel
) : RecyclerView.Adapter<ParticipantAdapter.ParticipantViewHolder>() {

    private var participants: List<ParticipantEntity> = emptyList()

    fun setParticipants(list: List<ParticipantEntity>) {
        participants = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParticipantViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.participant_item, parent, false)
        return ParticipantViewHolder(view)
    }

    override fun onBindViewHolder(holder: ParticipantViewHolder, position: Int) {
        val participant = participants[position]
        holder.bind(participant)
    }

    override fun getItemCount(): Int = participants.size

    inner class ParticipantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameText: EditText = itemView.findViewById(R.id.name_edittext)
        private val countText: EditText = itemView.findViewById(R.id.count_edittext)
        private val incrButton: Button = itemView.findViewById(R.id.incr_button)
        private val decrButton: Button = itemView.findViewById(R.id.decr_button)

        fun bind(participant: ParticipantEntity) {
            applyColorToButtons(itemView.context, incrButton, decrButton)

            nameText.setText(participant.name)
            countText.setText(participant.count.toString())

            incrButton.setOnClickListener {
                val updated = participant.copy(count = participant.count + 1)
                viewModel.updateParticipant(updated)
            }

            decrButton.setOnClickListener {
                if (participant.count > 0) {
                    val updated = participant.copy(count = participant.count - 1)
                    viewModel.updateParticipant(updated)
                }
            }
        }
    }
}