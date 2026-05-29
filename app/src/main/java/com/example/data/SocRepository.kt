package com.example.data

import kotlinx.coroutines.flow.Flow

class SocRepository(private val dao: SocDao) {
    val allLabs: Flow<List<LabEntity>> = dao.getAllLabs()
    val allAlerts: Flow<List<AlertEntity>> = dao.getAlerts()

    suspend fun initializeDefaultLabs() {
        // Pre-populate some labs if none exist
        val defaultLabs = listOf(
            LabEntity("lab_01", "Phishing Analysis", "Analyze an email header to identify phishing indicators.", false, "Email Security"),
            LabEntity("lab_02", "Firewall Log Hunting", "Filter denied connections to find beaconing activity.", false, "Network"),
            LabEntity("lab_03", "Malware Sandbox", "Review sandbox execution logs for a suspicious PDF.", false, "Malware")
        )
        dao.insertLabs(defaultLabs)
    }

    suspend fun markLabDone(id: String) {
        dao.markLabCompleted(id)
    }

    suspend fun addAlert(title: String, severity: String) {
        dao.insertAlert(AlertEntity(title = title, severity = severity))
    }
}
