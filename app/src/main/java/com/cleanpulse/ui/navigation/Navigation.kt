package com.cleanpulse.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cleanpulse.firebase.FirebaseManager
import com.cleanpulse.ui.screens.SplashScreen
import com.cleanpulse.ui.screens.HomeScreen
import com.cleanpulse.ui.screens.AnalysisScreen
import com.cleanpulse.ui.screens.CleaningScreen
import com.cleanpulse.ui.screens.ResultsScreen
import com.cleanpulse.ui.screens.SettingsScreen
import com.cleanpulse.ui.screens.HistoryScreen
import kotlinx.coroutines.delay

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Analysis : Screen("analysis")
    object Cleaning : Screen("cleaning")
    object Results : Screen("results")
    object Settings : Screen("settings")
    object History : Screen("history")
}

@Composable
fun AppNavigation(firebaseManager: FirebaseManager) {
    val navController = rememberNavController()
    val showSplash = remember { mutableStateOf(true) }
    
    LaunchedEffect(Unit) {
        delay(2000)  // Splash screen duration
        showSplash.value = false
    }
    
    if (showSplash.value) {
        SplashScreen()
    } else {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navController = navController,
                    firebaseManager = firebaseManager
                )
            }
            composable(Screen.Analysis.route) {
                AnalysisScreen(
                    navController = navController,
                    firebaseManager = firebaseManager
                )
            }
            composable(Screen.Cleaning.route) {
                CleaningScreen(
                    navController = navController,
                    firebaseManager = firebaseManager
                )
            }
            composable(Screen.Results.route) {
                ResultsScreen(
                    navController = navController,
                    firebaseManager = firebaseManager
                )
            }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    navController = navController,
                    firebaseManager = firebaseManager
                )
            }
            composable(Screen.History.route) {
                HistoryScreen(
                    navController = navController,
                    firebaseManager = firebaseManager
                )
            }
        }
    }
}
