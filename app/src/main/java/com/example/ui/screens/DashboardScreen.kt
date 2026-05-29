package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.foundation.border
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.viewmodels.DashboardViewModel
import com.example.ui.theme.AlertRed
import com.example.ui.theme.WarningYellow
import com.example.ui.theme.SuccessGreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(viewModel: DashboardViewModel, innerPadding: PaddingValues) {
    val alerts by viewModel.allAlerts.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        TopAppBar(title = { Text("SOCPocket", color = MaterialTheme.colorScheme.primary) })

        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "SIEM Overview",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            InfoCard(title = "Total Events", text = "1.2M", modifier = Modifier.weight(1f))
            InfoCard(title = "Active Threats", text = "${alerts.count { it.severity == "CRITICAL" }}", color = AlertRed, modifier = Modifier.weight(1f))
            InfoCard(title = "System Status", text = "Online", color = SuccessGreen, modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Real-time Alert Feed",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Button(
                onClick = {
                    val severities = listOf("CRITICAL", "HIGH", "MEDIUM")
                    val possibleAlerts = listOf("Failed SSH Login", "Suspicious PowerShell", "Malware Detected", "Data Exfiltration Anomaly")
                    viewModel.addMockAlert(possibleAlerts.random(), severities.random())
                },
                modifier = Modifier.testTag("simulate_alert_btn")
            ) {
                Text("Simulate")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            contentPadding = PaddingValues(bottom = 80.dp), // for bottom bar
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(alerts) { alert ->
                AlertItem(title = alert.title, severity = alert.severity)
            }
        }
        }
    }
}

@Composable
fun InfoCard(title: String, text: String, color: Color = MaterialTheme.colorScheme.onSurface, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.height(80.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, Color(0xFF1E293B))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            Text(text = text, style = MaterialTheme.typography.titleLarge, color = color)
        }
    }
}

@Composable
fun AlertItem(title: String, severity: String) {
    val color = when (severity) {
        "CRITICAL" -> AlertRed
        "HIGH" -> WarningYellow
        else -> MaterialTheme.colorScheme.secondary
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, Color(0xFF1E293B), RoundedCornerShape(16.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = "Alert Icon",
            tint = color
        )
        Column {
            Text(text = title, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
            Text(text = severity, style = MaterialTheme.typography.labelSmall, color = color)
        }
    }
}
