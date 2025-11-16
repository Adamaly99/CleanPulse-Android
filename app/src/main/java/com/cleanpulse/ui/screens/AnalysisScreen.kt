package com.cleanpulse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.cleanpulse.data.StorageCategory
import com.cleanpulse.data.StorageItem

@Composable
fun AnalysisScreen(
    navController: NavController,
    firebaseManager: FirebaseManager
) {
    var selectedCategory by remember { mutableStateOf(StorageCategory.IMAGES) }
    var items by remember { mutableStateOf(listOf<StorageItem>()) }
    var selectedItems by remember { mutableStateOf(setOf<String>()) }
    
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
                text = "Analyse du stockage",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )
        }
        
        Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
        
        // Category Tabs
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf(
                StorageCategory.IMAGES to "Images",
                StorageCategory.VIDEOS to "Vidéos",
                StorageCategory.APPS to "Apps",
                StorageCategory.OTHER to "Autres"
            ).forEach { (category, label) ->
                FilterChip(
                    selected = selectedCategory == category,
                    onClick = { selectedCategory = category },
                    label = { Text(label, fontSize = 12.sp) },
                    modifier = Modifier.height(32.dp)
                )
            }
        }
        
        Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
        
        // Items List
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(items.filter { it.category == selectedCategory }) { item ->
                StorageItemRow(
                    item = item,
                    isSelected = item.id in selectedItems,
                    onSelect = {
                        selectedItems = if (item.id in selectedItems) {
                            selectedItems - item.id
                        } else {
                            selectedItems + item.id
                        }
                    }
                )
            }
        }
        
        // Delete Button
        if (selectedItems.isNotEmpty()) {
            Button(
                onClick = { /* TODO: Delete selected items */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFF44336)
                )
            ) {
                Text(
                    "Supprimer ${selectedItems.size} élément${if (selectedItems.size > 1) "s" else ""}",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun StorageItemRow(
    item: StorageItem,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = if (isSelected) Color(0xFFE0F7FF) else Color(0xFFF5F5F5),
                shape = MaterialTheme.shapes.small
            )
            .clickable { onSelect() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A)
            )
            Text(
                text = "${item.size / 1024 / 1024} MB",
                fontSize = 12.sp,
                color = Color(0xFF666666)
            )
        }
        
        Checkbox(
            checked = isSelected,
            onCheckedChange = { onSelect() }
        )
    }
}
