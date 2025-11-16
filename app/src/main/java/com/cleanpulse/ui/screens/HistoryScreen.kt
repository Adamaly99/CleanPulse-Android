package com.cleanpulse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cleanpulse.firebase.FirebaseManager
import java.text.SimpleDateFormat
import java.util.*

data class HistoryItem(
    val date: Date,
    val spaceFreed: Long,
    val ramFreed: Long,
    val itemsDeleted: Int
)

@Composable
fun HistoryScreen(
    navController: NavController,
    firebaseManager: FirebaseManager
) {
    var historyItems by remember { mutableStateOf(listOf<HistoryItem>()) }
    
    LaunchedEffect(Unit) {
        // Mock data - in real app, fetch from Firestore
        historyItems = listOf(
            HistoryItem(Date(System.currentTimeMillis() - 86400000), 2500000000, 512000000, 1247),
            HistoryItem(Date(System.currentTimeMillis() - 172800000), 1800000000, 256000000, 892),
            HistoryItem(Date(System.currentTimeMillis() - 259200000), 3200000000, 768000000, 1567),
            HistoryItem(Date(System.currentTimeMillis() - 345600000), 1500000000, 384000000, 654),
        )
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color(0xFF00B4FF)
                )
            }
            Text(
                text = "Historique",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )
        }
        
        Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
        
        // History List
        if (historyItems.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Aucun historique",
                    fontSize = 16.sp,
                    color = Color(0xFF666666)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(historyItems) { item ->
                    HistoryItemCard(item)
                }
            }
        }
    }
}

@Composable
fun HistoryItemCard(item: HistoryItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFF5F5F5)
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Date
            Text(
                text = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(item.date),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                HistoryStat(
                    label = "Espace libéré",
                    value = "${item.spaceFreed / 1024 / 1024 / 1024} GB"
                )
                HistoryStat(
                    label = "RAM libérée",
                    value = "${item.ramFreed / 1024 / 1024} MB"
                )
                HistoryStat(
                    label = "Fichiers",
                    value = "${item.itemsDeleted}"
                )
            }
        }
    }
}

@Composable
fun HistoryStat(
    label: String,
    value: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF00B4FF)
        )
        Text(
            text = label,
            fontSize = 10.sp,
            color = Color(0xFF666666)
        )
    }
}
