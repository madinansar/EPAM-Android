package com.example.challengedraft.data.repository

import androidx.lifecycle.LiveData
import com.example.challengedraft.data.db.ChallengeDao
import com.example.challengedraft.data.models.ChallengeEntity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ChallengeRepository(private val challengeDao: ChallengeDao) {
    private val firestore = FirebaseFirestore.getInstance()
    private val challengesCollection = firestore.collection("challenges")

    val allChallenges: Flow<List<ChallengeEntity>> = challengeDao.getAllChallenges()

    fun getChallengeById(id: String) : LiveData<ChallengeEntity?> {
        return challengeDao.getChallengeById(id)
    }

    fun getChallengeByIdDirect(id: String): ChallengeEntity? {
        return challengeDao.getChallengeByIdDirect(id)
    }

    suspend fun insertChallenge(challenge: ChallengeEntity) {
        challengeDao.insert(challenge)
        challengesCollection.document(challenge.id).set(challenge)
    }

    suspend fun insertChallengeLocal(challenge: ChallengeEntity) {
        challengeDao.insert(challenge)
    }

    suspend fun updateChallenge(challenge: ChallengeEntity) {
        challengeDao.update(challenge)
        challengesCollection.document(challenge.id).set(challenge)
    }

    suspend fun deleteChallenge(challenge: ChallengeEntity) {
        challengeDao.delete(challenge)
        challengesCollection.document(challenge.id).delete()
    }

//    fun startFirestoreSync() {
//        challengesCollection.addSnapshotListener { data, error ->
//            if (error != null) {
//                return@addSnapshotListener
//            }
//            if (data != null) {
//                for (docChange in data.documentChanges) {
//                    val challenge = docChange.document.toObject(ChallengeEntity::class.java)
//                    CoroutineScope(Dispatchers.IO).launch {
//                        when (docChange.type) {
//                            com.google.firebase.firestore.DocumentChange.Type.ADDED -> {
//                                challengeDao.insert(challenge)  // insert locally
//                            }
//                            com.google.firebase.firestore.DocumentChange.Type.MODIFIED -> {
//                                challengeDao.update(challenge)  // update locally
//                            }
//                            com.google.firebase.firestore.DocumentChange.Type.REMOVED -> {
//                                challengeDao.delete(challenge) // update locally
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
}