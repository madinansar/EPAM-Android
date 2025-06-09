package com.example.challengedraft.data.db

import androidx.room.*
import com.example.challengedraft.data.models.ParticipantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ParticipantDao {
    @Query("SELECT * FROM participants")
    fun getAll(): Flow<List<ParticipantEntity>>

    @Query("SELECT * FROM participants WHERE challengeId = :challengeId")
    fun getByChallengeId(challengeId: String): Flow<List<ParticipantEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(participant: ParticipantEntity)

    @Update
    suspend fun update(participant: ParticipantEntity)

    @Delete
    suspend fun delete(participant: ParticipantEntity)

    @Query("DELETE FROM participants")
    suspend fun clearAll()
}
