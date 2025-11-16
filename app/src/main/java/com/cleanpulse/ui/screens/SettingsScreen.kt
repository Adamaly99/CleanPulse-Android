package com.cleanpulse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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

@Composable
fun SettingsScreen(
    navController: NavController,
    firebaseManager: FirebaseManager
) {
    var darkMode by remember { mutableStateOf(false) }
    var autoClean by remember { mutableStateOf(false) }
    var language by remember { mutableStateOf("FR") }
    
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
                text = "Paramètres",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A1A1A)
            )
        }
        
        Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            // Scheduler Section
            SettingsSectionTitle("Planificateur")
            
            SettingsToggleItem(
                title = "Nettoyage automatique",
                description = "Nettoyer automatiquement chaque jour",
                checked = autoClean,
                onCheckedChange = { autoClean = it }
            )
            
            Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
            
            // Language Section
            SettingsSectionTitle("Langue")
            
            SettingsRadioItem(
                title = "Français",
                selected = language == "FR",
                onClick = { language = "FR" }
            )
            
            SettingsRadioItem(
                title = "English",
                selected = language == "EN",
                onClick = { language = "EN" }
            )
            
            Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
            
            // Theme Section
            SettingsSectionTitle("Apparence")
            
            SettingsToggleItem(
                title = "Mode sombre",
                description = "Activer le mode sombre",
                checked = darkMode,
                onCheckedChange = { darkMode = it }
            )
            
            Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
            
            // Account Section
            SettingsSectionTitle("Compte")
            
            SettingsClickItem(
                title = "Connexion Google",
                description = "Connectez-vous avec votre compte Google",
                onClick = { /* TODO: Google Sign-In */ }
            )
            
            Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
            
            // Links Section
            SettingsSectionTitle("À propos")
            
            SettingsClickItem(
                title = "Politique de confidentialité",
                description = "Lire notre politique de confidentialité",
                onClick = { /* TODO: Open privacy policy */ }
            )
            
            SettingsClickItem(
                title = "Nous contacter",
                description = "Envoyer un email de support",
                onClick = { /* TODO: Open contact */ }
            )
            
            Spacer(modifier = Modifier.height(32.dp))
        }
        
        // Logout Button
        Button(
            onClick = {
                firebaseManager.signOut()
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF44336)
            )
        ) {
            Text(
                "Déconnexion",
                color = Color.White,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun SettingsSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFF00B4FF),
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun SettingsToggleItem(
    title: String,
    description: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!checked) }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A)
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color(0xFF666666)
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
fun SettingsRadioItem(
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick
        )
        Text(
            text = title,
            fontSize = 16.sp,
            color = Color(0xFF1A1A1A),
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}

@Composable
fun SettingsClickItem(
    title: String,
    description: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF1A1A1A)
            )
            Text(
                text = description,
                fontSize = 12.sp,
                color = Color(0xFF666666)
            )
        }
    }
}
