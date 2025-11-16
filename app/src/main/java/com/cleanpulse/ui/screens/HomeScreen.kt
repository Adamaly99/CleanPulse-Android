package com.cleanpulse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cleanpulse.firebase.FirebaseManager
import com.cleanpulse.system.SystemManager
import com.cleanpulse.ui.navigation.Screen
import androidx.compose.foundation.Canvas
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings

@Composable
fun HomeScreen(
    navController: NavController,
    firebaseManager: FirebaseManager
) {
    var cleanliness by remember { mutableStateOf(0) }
    var isLoading by remember { mutableStateOf(false) }
    
    // Get system manager (in real app, inject via context)
    val systemManager = remember { SystemManager(androidx.compose.ui.platform.LocalContext.current) }
    
    LaunchedEffect(Unit) {
        cleanliness = systemManager.getCleanlinessPercentage()
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "CleanPulse",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
                Text(
                    text = "Nettoyeur & Booster",
                    fontSize = 12.sp,
                    color = Color(0xFF666666)
                )
            }
            IconButton(onClick = { navController.navigate(Screen.Settings.route) }) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings",
                    tint = Color(0xFF00B4FF)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // Cleanliness Indicator (Circular Progress)
        Box(
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val radius = size.minDimension / 2
                val strokeWidth = 12.dp.toPx()
                
                // Background circle
                drawCircle(
                    color = Color(0xFFF5F5F5),
                    radius = radius,
                    style = Stroke(strokeWidth)
                )
                
                // Progress circle
                val sweepAngle = (cleanliness / 100f) * 360f
                drawArc(
                    color = Color(0xFF00B4FF),
                    startAngle = -90f,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    style = Stroke(strokeWidth)
                )
            }
            
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.align(Alignment.Center)
            ) {
                Text(
                    text = "$cleanliness%",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF00B4FF)
                )
                Text(
                    text = "Propreté",
                    fontSize = 14.sp,
                    color = Color(0xFF666666)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Main Clean Button (Circular)
        Box(
            modifier = Modifier
                .size(140.dp)
                .align(Alignment.CenterHorizontally)
                .clip(CircleShape)
                .background(Color(0xFF00B4FF))
                .clickable {
                    isLoading = true
                    navController.navigate(Screen.Cleaning.route)
                },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Nettoyer",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "maintenant",
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Secondary Buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SecondaryButton(
                text = "Analyse",
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate(Screen.Analysis.route) }
            )
            SecondaryButton(
                text = "Booster",
                modifier = Modifier.weight(1f),
                onClick = { /* TODO */ }
            )
        }
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SecondaryButton(
                text = "Refroidir",
                modifier = Modifier.weight(1f),
                onClick = { /* TODO */ }
            )
            SecondaryButton(
                text = "Historique",
                modifier = Modifier.weight(1f),
                onClick = { navController.navigate(Screen.History.route) }
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun SecondaryButton(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(48.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFF5F5F5)
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Text(
            text = text,
            color = Color(0xFF1A1A1A),
            fontWeight = FontWeight.SemiBold
        )
    }
}
