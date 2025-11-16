package com.cleanpulse.data

import java.util.Date

/**
 * Data models for CleanPulse application
 */

// User Models
data class User(
    val uid: String = "",
    val email: String = "",
    val displayName: String = "",
    val photoUrl: String = "",
    val createdAt: Date = Date(),
    val lastLogin: Date = Date(),
    val totalSpaceFreed: Long = 0,
    val totalRamFreed: Long = 0,
    val cleaningCount: Int = 0
)

// Cleaning Models
data class StorageItem(
    val id: String = "",
    val name: String = "",
    val size: Long = 0,
    val category: StorageCategory = StorageCategory.OTHER,
    val path: String = "",
    val lastModified: Date = Date(),
    val isSelected: Boolean = false
)

enum class StorageCategory {
    IMAGES,
    VIDEOS,
    APPS,
    OTHER,
    CACHE,
    TEMP_FILES
}

data class CleaningResult(
    val totalItemsDeleted: Int = 0,
    val spaceFreed: Long = 0,
    val ramFreed: Long = 0,
    val duration: Long = 0,
    val timestamp: Date = Date(),
    val deletedItems: List<String> = emptyList(),
    val success: Boolean = false
)

data class CleaningStats(
    val uid: String = "",
    val totalCleanings: Int = 0,
    val totalSpaceFreed: Long = 0,
    val totalRamFreed: Long = 0,
    val lastCleaningDate: Date = Date(),
    val averageSpacePerCleaning: Long = 0,
    val averageTimePerCleaning: Long = 0
)

// Log Models
data class ActionLog(
    val uid: String = "",
    val action: String = "",
    val timestamp: Date = Date(),
    val details: Map<String, Any> = emptyMap(),
    val success: Boolean = true,
    val errorMessage: String = ""
)

// Device Info Models
data class DeviceInfo(
    val totalStorage: Long = 0,
    val availableStorage: Long = 0,
    val usedStorage: Long = 0,
    val totalRam: Long = 0,
    val availableRam: Long = 0,
    val usedRam: Long = 0,
    val cpuTemperature: Float = 0f,
    val batteryLevel: Int = 0,
    val isCharging: Boolean = false
)

// App Info Models
data class AppInfo(
    val packageName: String = "",
    val appName: String = "",
    val size: Long = 0,
    val cacheSize: Long = 0,
    val dataSize: Long = 0,
    val version: String = "",
    val lastUpdated: Date = Date(),
    val isSystemApp: Boolean = false
)

// Recommendation Models
data class Recommendation(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val priority: RecommendationPriority = RecommendationPriority.LOW,
    val action: String = "",
    val estimatedSpaceSavings: Long = 0
)

enum class RecommendationPriority {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}
