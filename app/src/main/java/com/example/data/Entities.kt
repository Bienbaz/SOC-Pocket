package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "labs")
data class LabEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val type: String // E.g. "Phishing", "Log Analysis", "Malware"
)

@Entity(tableName = "notifications")
data class AlertEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val severity: String, // "CRITICAL", "HIGH", "MEDIUM", "LOW"
    val timestamp: Long = System.currentTimeMillis()
)
