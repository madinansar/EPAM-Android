package com.example.challengedraft.data.repository

import com.example.challengedraft.data.db.ParticipantDao
import com.example.challengedraft.data.models.ParticipantEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ParticipantRepository(private val participantDao: ParticipantDao,
                            private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) {
    fun syncFromFirestore(challengeId: String) {
        firestore.collection("participants")
            .whereEqualTo("challengeId", challengeId)
            .addSnapshotListener { snapshot, error ->
                if (error != null || snapshot == null) return@addSnapshotListener

                val participants = snapshot.documents.mapNotNull {
                    it.toObject(ParticipantEntity::class.java)
                }
                // Insert or update all Firestore participants into Room DB
                CoroutineScope(Dispatchers.IO).launch {
                    participants.forEach { participantDao.insert(it) }
                }
            }
    }

    fun getParticipantsByChallengeId(challengeId: String): Flow<List<ParticipantEntity>> {
        return participantDao.getByChallengeId(challengeId)
    }

    suspend fun insertParticipant(participant: ParticipantEntity) {
        participantDao.insert(participant)
        firestore.collection("participants").document(participant.id).set(participant)
    }

    suspend fun updateParticipant(participant: ParticipantEntity) {
        participantDao.update(participant)
        firestore.collection("participants").document(participant.id).set(participant)
    }

    suspend fun deleteParticipant(participant: ParticipantEntity) {
        participantDao.delete(participant)
        firestore.collection("participants").document(participant.id).delete()
    }
}