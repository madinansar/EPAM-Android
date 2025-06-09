package com.example.challengedraft.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "challenges")
data class ChallengeEntity(
    @PrimaryKey val id: String = "",
    val title: String = ""
)




