package com.cleanpulse

/**
 * Application-wide constants and configuration
 */
object Constants {
    
    // App Info
    const val APP_NAME = "CleanPulse"
    const val APP_VERSION = "1.0.0"
    
    // Design Colors
    const val PRIMARY_COLOR = "#00B4FF"
    const val PRIMARY_VARIANT = "#0099CC"
    const val SECONDARY_COLOR = "#1A1A1A"
    const val BACKGROUND_COLOR = "#FFFFFF"
    const val SURFACE_COLOR = "#F5F5F5"
    
    // Firebase Configuration
    // Note: Replace with your actual Firebase project configuration
    const val FIREBASE_PROJECT_ID = "cleanpulse-app"
    
    // AdMob Configuration - TEST IDS (Replace with real IDs for production)
    const val ADMOB_APP_ID = "ca-app-pub-xxxxxxxxxxxxxxxx~yyyyyyyyyy"
    const val ADMOB_BANNER_AD_ID = "ca-app-pub-3940256099942544/6300978111"  // Test Banner
    const val ADMOB_INTERSTITIAL_AD_ID = "ca-app-pub-3940256099942544/1033173712"  // Test Interstitial
    const val ADMOB_REWARDED_AD_ID = "ca-app-pub-3940256099942544/5224354917"  // Test Rewarded
    
    // Shared Preferences Keys
    const val PREF_USER_ID = "user_id"
    const val PREF_USER_EMAIL = "user_email"
    const val PREF_LAST_CLEAN_TIME = "last_clean_time"
    const val PREF_TOTAL_SPACE_FREED = "total_space_freed"
    const val PREF_TOTAL_RAM_FREED = "total_ram_freed"
    const val PREF_LANGUAGE = "language"
    const val PREF_DARK_MODE = "dark_mode"
    const val PREF_AUTO_CLEAN = "auto_clean"
    const val PREF_AUTO_CLEAN_INTERVAL = "auto_clean_interval"
    
    // Language Codes
    const val LANGUAGE_FRENCH = "fr"
    const val LANGUAGE_ENGLISH = "en"
    
    // Cleaning Configuration
    const val CACHE_CLEAR_THRESHOLD = 10 * 1024 * 1024  // 10 MB
    const val TEMP_FILE_CLEANUP_ENABLED = true
    const val AUTO_CLEAN_DEFAULT_INTERVAL = 24 * 60  // 24 hours in minutes
    
    // Firestore Collections
    const val FIRESTORE_USERS_COLLECTION = "users"
    const val FIRESTORE_LOGS_COLLECTION = "logs"
    const val FIRESTORE_STATS_COLLECTION = "stats"
    
    // Analytics Events
    const val ANALYTICS_EVENT_CLEAN_START = "clean_start"
    const val ANALYTICS_EVENT_CLEAN_COMPLETE = "clean_complete"
    const val ANALYTICS_EVENT_DEEP_CLEAN_VIEWED = "deep_clean_viewed"
    const val ANALYTICS_EVENT_AD_REWARDED = "ad_rewarded"
    const val ANALYTICS_EVENT_APP_OPENED = "app_opened"
    const val ANALYTICS_EVENT_USER_SIGNED_IN = "user_signed_in"
    
    // URLs
    const val PRIVACY_POLICY_URL = "https://example.com/privacy"
    const val CONTACT_EMAIL = "support@cleanpulse.app"
    const val WEBSITE_URL = "https://cleanpulse.app"
}
