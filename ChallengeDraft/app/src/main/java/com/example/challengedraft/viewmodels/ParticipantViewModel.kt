package com.example.challengedraft.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.challengedraft.data.db.AppDatabase
import com.example.challengedraft.data.models.ParticipantEntity
import com.example.challengedraft.data.repository.ParticipantRepository
import kotlinx.coroutines.launch

class ParticipantViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ParticipantRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = ParticipantRepository(db.participantDao())
    }

    fun startSync(challengeId: String) {
        repository.syncFromFirestore(challengeId)
    }

    fun getParticipantsByChallengeId(challengeId: String): LiveData<List<ParticipantEntity>> {
        return repository.getParticipantsByChallengeId(challengeId).asLiveData()
    }

    fun insertParticipant(participant: ParticipantEntity) = viewModelScope.launch {
        repository.insertParticipant(participant)
    }

    fun updateParticipant(participant: ParticipantEntity) = viewModelScope.launch {
        repository.updateParticipant(participant)
    }

    fun deleteParticipant(participant: ParticipantEntity) = viewModelScope.launch {
        repository.deleteParticipant(participant)
    }
}
