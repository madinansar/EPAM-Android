package com.example.challengedraft.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.challengedraft.data.models.ChallengeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChallengeDao {
    @Query("SELECT * FROM challenges")
    fun getAllChallenges(): Flow<List<ChallengeEntity>>

    @Query("SELECT * FROM challenges WHERE id = :id")
    fun getChallengeById(id: String): LiveData<ChallengeEntity?>

    @Query("SELECT * FROM challenges WHERE id = :id")
    fun getChallengeByIdDirect(id: String): ChallengeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(challenge: ChallengeEntity)

    @Update
    suspend fun update(challenge: ChallengeEntity)

    @Delete
    suspend fun delete(challenge: ChallengeEntity)
}
