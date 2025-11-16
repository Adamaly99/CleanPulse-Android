package com.cleanpulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.cleanpulse.firebase.FirebaseManager
import com.cleanpulse.ui.theme.CleanPulseTheme
import com.cleanpulse.ui.screens.SplashScreen
import com.cleanpulse.ui.screens.HomeScreen
import com.cleanpulse.ui.navigation.AppNavigation
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    
    private lateinit var firebaseManager: FirebaseManager
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        firebaseManager = FirebaseManager()
        
        setContent {
            CleanPulseTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    color = Color.White
                ) {
                    AppNavigation(firebaseManager = firebaseManager)
                }
            }
        }
    }
}
