package com.example.challengedraft.views
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.challengedraft.R
import com.example.challengedraft.data.models.ChallengeEntity

class ChallengeAdapter(
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<ChallengeAdapter.ChallengeViewHolder>() {

    private val challenges = mutableListOf<ChallengeEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.challenge_item, parent, false)
        return ChallengeViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val challenge = challenges[position]
        holder.bind(challenge)
    }

    override fun getItemCount(): Int = challenges.size

    fun setChallenges(newChallenges: List<ChallengeEntity>) {
        challenges.clear()
        challenges.addAll(newChallenges)
        notifyDataSetChanged()
    }

    inner class ChallengeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.challengeItemTitle)
        private val codeTextView: TextView = itemView.findViewById(R.id.challengeItemCode)

        fun bind(challenge: ChallengeEntity) {
            titleTextView.text = challenge.title
            codeTextView.text = challenge.id
            itemView.setOnClickListener {
                onItemClick(challenge.id)
            }
        }
    }
}
