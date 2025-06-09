package com.example.challengedraft.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "participants")
data class ParticipantEntity(
    @PrimaryKey val id: String = "-1",
    val challengeId: String = "-1",
    val name: String = "",
    var count: Int = 0
)




