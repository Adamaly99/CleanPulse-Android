package com.cleanpulse.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.cleanpulse.ui.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun CleaningScreen(
    navController: NavController,
    firebaseManager: FirebaseManager
) {
    var progress by remember { mutableStateOf(0f) }
    var deletedItems by remember { mutableStateOf(listOf<String>()) }
    var isComplete by remember { mutableStateOf(false) }
    
    val progressAnimatable = remember { Animatable(0f) }
    
    LaunchedEffect(Unit) {
        // Simulate cleaning progress
        while (progress < 100f) {
            delay(100)
            progress += (Math.random() * 10).toFloat()
            if (progress > 100f) progress = 100f
            progressAnimatable.animateTo(progress, animationSpec = tween(100))
            
            // Add deleted items
            if (progress > 20f && deletedItems.isEmpty()) {
                deletedItems = listOf("cache_file_1.tmp", "temp_data.db")
            }
            if (progress > 50f && deletedItems.size < 5) {
                deletedItems = deletedItems + "old_log_${deletedItems.size}.log"
            }
        }
        
        isComplete = true
        delay(1000)
        navController.navigate(Screen.Results.route) {
            popUpTo(Screen.Home.route)
        }
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        
        // Progress Circle
        Box(
            modifier = Modifier
                .size(200.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = { progress / 100f },
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFF00B4FF),
                strokeWidth = 12.dp
            )
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = "${progress.toInt()}%",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00B4FF)
                )
                Text(
                    text = "Nettoyage en cours",
                    fontSize = 12.sp,
                    color = Color(0xFF666666)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Deleted Items
        if (deletedItems.isNotEmpty()) {
            Text(
                text = "${deletedItems.size} éléments supprimés",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A),
                modifier = Modifier.padding(16.dp)
            )
            
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(deletedItems) { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFF5F5F5), MaterialTheme.shapes.small)
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "✓",
                            fontSize = 16.sp,
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = item,
                            fontSize = 12.sp,
                            color = Color(0xFF666666)
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Deep Clean Button
        if (progress > 50f && !isComplete) {
            Button(
                onClick = { /* TODO: Show rewarded ad */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF59E0B)
                )
            ) {
                Text(
                    "Nettoyage approfondi",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}
