package com.example.challengedraft.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.challengedraft.data.models.ChallengeEntity
import com.example.challengedraft.data.models.ParticipantEntity

@Database(entities = [ParticipantEntity::class, ChallengeEntity::class], version = 9)
abstract class AppDatabase : RoomDatabase() {
    abstract fun participantDao(): ParticipantDao
    abstract fun challengeDao(): ChallengeDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "challenge_tracker_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
