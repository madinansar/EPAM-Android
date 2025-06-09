package com.example.challengedraft.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.challengedraft.data.db.AppDatabase
import com.example.challengedraft.data.models.ChallengeEntity
import com.example.challengedraft.data.repository.ChallengeRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
class ChallengeViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ChallengeRepository
    val allChallenges: LiveData<List<ChallengeEntity>>

    init {
        val db = AppDatabase.getDatabase(application)
        repository = ChallengeRepository(db.challengeDao())
        allChallenges = repository.allChallenges.asLiveData()
//
//        repository.startFirestoreSync()
    }

    fun getChallengeById(id: String): LiveData<ChallengeEntity?> {
        return repository.getChallengeById(id)
    }

    fun getChallengeByIdDirect(id: String): ChallengeEntity? {
        return repository.getChallengeByIdDirect(id)
    }

    fun insertChallenge(challenge: ChallengeEntity) = viewModelScope.launch {
        repository.insertChallenge(challenge)
    }

    fun insertChallengeLocal(challenge: ChallengeEntity) = viewModelScope.launch {
        repository.insertChallengeLocal(challenge)
    }

    fun updateChallenge(challenge: ChallengeEntity) = viewModelScope.launch {
        repository.updateChallenge(challenge)
    }

    fun deleteChallenge(challenge: ChallengeEntity) = viewModelScope.launch {
        repository.deleteChallenge(challenge)
    }

    fun joinChallenge(roomCode: String, onSuccess: (ChallengeEntity) -> Unit, onFailure: () -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("challenges").document(roomCode)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val challenge = document.toObject(ChallengeEntity::class.java)
                    if (challenge != null) {
                        onSuccess(challenge)
                    } else {
                        onFailure()
                    }
                } else {
                    onFailure()
                }
            }
            .addOnFailureListener {
                onFailure()
            }
    }
}
