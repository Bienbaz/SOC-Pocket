package com.example.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SocDao {
    @Query("SELECT * FROM labs")
    fun getAllLabs(): Flow<List<LabEntity>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLabs(labs: List<LabEntity>)

    @Query("UPDATE labs SET isCompleted = 1 WHERE id = :labId")
    suspend fun markLabCompleted(labId: String)

    @Query("SELECT * FROM notifications ORDER BY timestamp DESC")
    fun getAlerts(): Flow<List<AlertEntity>>

    @Insert
    suspend fun insertAlert(alert: AlertEntity)
}
