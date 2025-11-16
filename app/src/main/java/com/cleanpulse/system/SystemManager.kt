package com.cleanpulse.system

import android.app.ActivityManager
import android.app.usage.StorageStatsManager
import android.content.Context
import android.os.Build
import android.os.Debug
import android.os.Environment
import android.os.StatFs
import android.util.Log
import com.cleanpulse.data.DeviceInfo
import com.cleanpulse.data.StorageItem
import com.cleanpulse.data.StorageCategory
import java.io.File
import java.util.Date

/**
 * Manages system operations: storage analysis, cache cleaning, RAM optimization
 */
class SystemManager(private val context: Context) {
    
    private val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    
    companion object {
        private const val TAG = "SystemManager"
    }
    
    // ========== STORAGE ANALYSIS ==========
    
    /**
     * Get device storage information
     */
    fun getDeviceInfo(): DeviceInfo {
        val stat = StatFs(Environment.getDataDirectory().path)
        val totalStorage = stat.totalBytes
        val availableStorage = stat.availableBytes
        val usedStorage = totalStorage - availableStorage
        
        val runtime = Runtime.getRuntime()
        val totalRam = runtime.totalMemory()
        val availableRam = runtime.freeMemory()
        val usedRam = totalRam - availableRam
        
        return DeviceInfo(
            totalStorage = totalStorage,
            availableStorage = availableStorage,
            usedStorage = usedStorage,
            totalRam = totalRam,
            availableRam = availableRam,
            usedRam = usedRam,
            cpuTemperature = getCpuTemperature(),
            batteryLevel = getBatteryLevel(),
            isCharging = isDeviceCharging()
        )
    }
    
    /**
     * Get storage cleanliness percentage
     */
    fun getCleanlinessPercentage(): Int {
        val deviceInfo = getDeviceInfo()
        return if (deviceInfo.totalStorage > 0) {
            ((deviceInfo.availableStorage * 100) / deviceInfo.totalStorage).toInt()
        } else {
            0
        }
    }
    
    /**
     * Scan for cache files
     */
    fun scanCacheFiles(): List<StorageItem> {
        val cacheItems = mutableListOf<StorageItem>()
        
        // App cache directory
        val cacheDir = context.cacheDir
        scanDirectory(cacheDir, StorageCategory.CACHE, cacheItems)
        
        // External cache directory
        val externalCacheDir = context.externalCacheDir
        if (externalCacheDir != null && externalCacheDir.exists()) {
            scanDirectory(externalCacheDir, StorageCategory.CACHE, cacheItems)
        }
        
        return cacheItems
    }
    
    /**
     * Scan for temporary files
     */
    fun scanTempFiles(): List<StorageItem> {
        val tempItems = mutableListOf<StorageItem>()
        val tempDir = File(Environment.getExternalStorageDirectory(), "Android/data")
        
        if (tempDir.exists()) {
            scanDirectory(tempDir, StorageCategory.TEMP_FILES, tempItems)
        }
        
        return tempItems
    }
    
    /**
     * Scan for images
     */
    fun scanImages(): List<StorageItem> {
        val imageItems = mutableListOf<StorageItem>()
        val picturesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        
        if (picturesDir.exists()) {
            scanDirectory(picturesDir, StorageCategory.IMAGES, imageItems)
        }
        
        return imageItems
    }
    
    /**
     * Scan for videos
     */
    fun scanVideos(): List<StorageItem> {
        val videoItems = mutableListOf<StorageItem>()
        val moviesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES)
        
        if (moviesDir.exists()) {
            scanDirectory(moviesDir, StorageCategory.VIDEOS, videoItems)
        }
        
        return videoItems
    }
    
    /**
     * Recursively scan directory for files
     */
    private fun scanDirectory(
        directory: File,
        category: StorageCategory,
        items: MutableList<StorageItem>
    ) {
        try {
            val files = directory.listFiles() ?: return
            
            for (file in files) {
                if (file.isFile) {
                    items.add(
                        StorageItem(
                            id = file.absolutePath,
                            name = file.name,
                            size = file.length(),
                            category = category,
                            path = file.absolutePath,
                            lastModified = Date(file.lastModified())
                        )
                    )
                } else if (file.isDirectory) {
                    scanDirectory(file, category, items)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error scanning directory: ${directory.absolutePath}", e)
        }
    }
    
    // ========== CACHE CLEANING ==========
    
    /**
     * Clear app cache
     */
    fun clearAppCache(): Long {
        var freedSpace = 0L
        
        try {
            // Clear app cache directory
            val cacheDir = context.cacheDir
            freedSpace += deleteDirectory(cacheDir)
            
            // Clear external cache directory
            val externalCacheDir = context.externalCacheDir
            if (externalCacheDir != null && externalCacheDir.exists()) {
                freedSpace += deleteDirectory(externalCacheDir)
            }
            
            Log.d(TAG, "Cache cleared: $freedSpace bytes")
        } catch (e: Exception) {
            Log.e(TAG, "Error clearing cache", e)
        }
        
        return freedSpace
    }
    
    /**
     * Delete files in a directory
     */
    private fun deleteDirectory(directory: File): Long {
        var freedSpace = 0L
        
        try {
            val files = directory.listFiles() ?: return 0L
            
            for (file in files) {
                if (file.isFile) {
                    freedSpace += file.length()
                    file.delete()
                } else if (file.isDirectory) {
                    freedSpace += deleteDirectory(file)
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error deleting directory: ${directory.absolutePath}", e)
        }
        
        return freedSpace
    }
    
    /**
     * Delete specific files
     */
    fun deleteFiles(filePaths: List<String>): Long {
        var freedSpace = 0L
        
        for (path in filePaths) {
            try {
                val file = File(path)
                if (file.exists()) {
                    freedSpace += file.length()
                    file.delete()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error deleting file: $path", e)
            }
        }
        
        return freedSpace
    }
    
    // ========== RAM OPTIMIZATION ==========
    
    /**
     * Get available RAM
     */
    fun getAvailableRam(): Long {
        val runtime = Runtime.getRuntime()
        return runtime.freeMemory()
    }
    
    /**
     * Get used RAM
     */
    fun getUsedRam(): Long {
        val runtime = Runtime.getRuntime()
        return runtime.totalMemory() - runtime.freeMemory()
    }
    
    /**
     * Get total RAM
     */
    fun getTotalRam(): Long {
        val runtime = Runtime.getRuntime()
        return runtime.totalMemory()
    }
    
    /**
     * Force garbage collection (limited effect)
     */
    fun optimizeRam(): Long {
        val beforeRam = getAvailableRam()
        System.gc()
        Thread.sleep(100)
        val afterRam = getAvailableRam()
        
        return afterRam - beforeRam
    }
    
    /**
     * Kill background processes (with caution)
     */
    fun killBackgroundProcesses(packageNames: List<String>): Int {
        var killedCount = 0
        
        for (packageName in packageNames) {
            try {
                activityManager.killBackgroundProcesses(packageName)
                killedCount++
            } catch (e: Exception) {
                Log.e(TAG, "Error killing process: $packageName", e)
            }
        }
        
        return killedCount
    }
    
    // ========== DEVICE MONITORING ==========
    
    /**
     * Get CPU temperature (may not be available on all devices)
     */
    private fun getCpuTemperature(): Float {
        return try {
            val file = File("/sys/class/thermal/thermal_zone0/temp")
            if (file.exists()) {
                val temp = file.readText().trim().toFloatOrNull() ?: 0f
                temp / 1000f  // Convert to Celsius
            } else {
                0f
            }
        } catch (e: Exception) {
            Log.w(TAG, "Could not read CPU temperature", e)
            0f
        }
    }
    
    /**
     * Get battery level
     */
    private fun getBatteryLevel(): Int {
        return try {
            val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as android.os.BatteryManager
            batteryManager.getIntProperty(android.os.BatteryManager.BATTERY_PROPERTY_CAPACITY)
        } catch (e: Exception) {
            Log.w(TAG, "Could not read battery level", e)
            0
        }
    }
    
    /**
     * Check if device is charging
     */
    private fun isDeviceCharging(): Boolean {
        return try {
            val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as android.os.BatteryManager
            val status = batteryManager.getIntProperty(android.os.BatteryManager.BATTERY_PROPERTY_STATUS)
            status == android.os.BatteryManager.BATTERY_STATUS_CHARGING || 
            status == android.os.BatteryManager.BATTERY_STATUS_FULL
        } catch (e: Exception) {
            Log.w(TAG, "Could not check charging status", e)
            false
        }
    }
}
