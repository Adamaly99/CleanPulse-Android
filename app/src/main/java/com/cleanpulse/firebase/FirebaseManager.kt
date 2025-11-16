package com.cleanpulse.firebase

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.analytics.FirebaseAnalytics
import com.cleanpulse.data.User
import com.cleanpulse.data.ActionLog
import com.cleanpulse.data.CleaningStats
import com.cleanpulse.Constants
import kotlinx.coroutines.tasks.await
import java.util.Date

/**
 * Manages Firebase operations (Auth, Firestore, Storage, Analytics)
 */
class FirebaseManager {
    
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val analytics = FirebaseAnalytics.getInstance(null)
    
    companion object {
        private const val TAG = "FirebaseManager"
    }
    
    // ========== AUTHENTICATION ==========
    
    /**
     * Sign in with Google credentials
     */
    suspend fun signInWithGoogle(idToken: String): Result<User> = try {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        val authResult = auth.signInWithCredential(credential).await()
        val firebaseUser = authResult.user
        
        if (firebaseUser != null) {
            val user = User(
                uid = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                displayName = firebaseUser.displayName ?: "",
                photoUrl = firebaseUser.photoUrl?.toString() ?: ""
            )
            
            // Save user to Firestore
            saveUserToFirestore(user)
            
            // Log sign-in event
            logAction(user.uid, "user_signed_in", mapOf("email" to user.email))
            
            // Analytics event
            analytics.logEvent("user_signed_in", null)
            
            Result.success(user)
        } else {
            Result.failure(Exception("Authentication failed: user is null"))
        }
    } catch (e: Exception) {
        Log.e(TAG, "Google sign-in failed", e)
        Result.failure(e)
    }
    
    /**
     * Sign in with email and password
     */
    suspend fun signInWithEmail(email: String, password: String): Result<User> = try {
        val authResult = auth.signInWithEmailAndPassword(email, password).await()
        val firebaseUser = authResult.user
        
        if (firebaseUser != null) {
            val user = User(
                uid = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                displayName = firebaseUser.displayName ?: ""
            )
            
            logAction(user.uid, "user_signed_in", mapOf("method" to "email"))
            analytics.logEvent("user_signed_in", null)
            
            Result.success(user)
        } else {
            Result.failure(Exception("Authentication failed"))
        }
    } catch (e: Exception) {
        Log.e(TAG, "Email sign-in failed", e)
        Result.failure(e)
    }
    
    /**
     * Create account with email and password
     */
    suspend fun createAccountWithEmail(email: String, password: String): Result<User> = try {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        val firebaseUser = authResult.user
        
        if (firebaseUser != null) {
            val user = User(
                uid = firebaseUser.uid,
                email = firebaseUser.email ?: ""
            )
            
            saveUserToFirestore(user)
            logAction(user.uid, "account_created", mapOf("email" to email))
            analytics.logEvent("account_created", null)
            
            Result.success(user)
        } else {
            Result.failure(Exception("Account creation failed"))
        }
    } catch (e: Exception) {
        Log.e(TAG, "Account creation failed", e)
        Result.failure(e)
    }
    
    /**
     * Sign out current user
     */
    fun signOut() {
        auth.signOut()
        analytics.logEvent("user_signed_out", null)
    }
    
    /**
     * Get current user
     */
    fun getCurrentUser(): User? {
        val firebaseUser = auth.currentUser ?: return null
        return User(
            uid = firebaseUser.uid,
            email = firebaseUser.email ?: "",
            displayName = firebaseUser.displayName ?: "",
            photoUrl = firebaseUser.photoUrl?.toString() ?: ""
        )
    }
    
    /**
     * Check if user is authenticated
     */
    fun isAuthenticated(): Boolean = auth.currentUser != null
    
    // ========== FIRESTORE ==========
    
    /**
     * Save user data to Firestore
     */
    private suspend fun saveUserToFirestore(user: User) = try {
        firestore.collection(Constants.FIRESTORE_USERS_COLLECTION)
            .document(user.uid)
            .set(user)
            .await()
        Log.d(TAG, "User saved to Firestore: ${user.uid}")
    } catch (e: Exception) {
        Log.e(TAG, "Failed to save user to Firestore", e)
    }
    
    /**
     * Get user data from Firestore
     */
    suspend fun getUserFromFirestore(uid: String): Result<User> = try {
        val document = firestore.collection(Constants.FIRESTORE_USERS_COLLECTION)
            .document(uid)
            .get()
            .await()
        
        val user = document.toObject(User::class.java)
        if (user != null) {
            Result.success(user)
        } else {
            Result.failure(Exception("User not found"))
        }
    } catch (e: Exception) {
        Log.e(TAG, "Failed to get user from Firestore", e)
        Result.failure(e)
    }
    
    /**
     * Update user cleaning stats
     */
    suspend fun updateUserStats(uid: String, spaceFreed: Long, ramFreed: Long) = try {
        firestore.collection(Constants.FIRESTORE_USERS_COLLECTION)
            .document(uid)
            .update(
                "totalSpaceFreed" to com.google.firebase.firestore.FieldValue.increment(spaceFreed),
                "totalRamFreed" to com.google.firebase.firestore.FieldValue.increment(ramFreed),
                "cleaningCount" to com.google.firebase.firestore.FieldValue.increment(1),
                "lastLogin" to Date()
            )
            .await()
        Log.d(TAG, "User stats updated: $uid")
    } catch (e: Exception) {
        Log.e(TAG, "Failed to update user stats", e)
    }
    
    /**
     * Save cleaning stats
     */
    suspend fun saveCleaningStats(uid: String, stats: CleaningStats) = try {
        firestore.collection(Constants.FIRESTORE_STATS_COLLECTION)
            .document(uid)
            .set(stats)
            .await()
        Log.d(TAG, "Cleaning stats saved: $uid")
    } catch (e: Exception) {
        Log.e(TAG, "Failed to save cleaning stats", e)
    }
    
    /**
     * Log user action
     */
    suspend fun logAction(uid: String, action: String, details: Map<String, Any> = emptyMap()) = try {
        val log = ActionLog(
            uid = uid,
            action = action,
            timestamp = Date(),
            details = details,
            success = true
        )
        
        firestore.collection(Constants.FIRESTORE_LOGS_COLLECTION)
            .add(log)
            .await()
        Log.d(TAG, "Action logged: $action for user $uid")
    } catch (e: Exception) {
        Log.e(TAG, "Failed to log action", e)
    }
    
    /**
     * Get user action logs
     */
    suspend fun getUserLogs(uid: String, limit: Long = 100): Result<List<ActionLog>> = try {
        val logs = firestore.collection(Constants.FIRESTORE_LOGS_COLLECTION)
            .whereEqualTo("uid", uid)
            .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
            .limit(limit)
            .get()
            .await()
            .toObjects(ActionLog::class.java)
        
        Result.success(logs)
    } catch (e: Exception) {
        Log.e(TAG, "Failed to get user logs", e)
        Result.failure(e)
    }
    
    // ========== ANALYTICS ==========
    
    /**
     * Log cleaning start event
     */
    fun logCleaningStart() {
        analytics.logEvent(Constants.ANALYTICS_EVENT_CLEAN_START, null)
    }
    
    /**
     * Log cleaning complete event
     */
    fun logCleaningComplete(spaceFreed: Long, duration: Long) {
        val bundle = android.os.Bundle().apply {
            putLong("space_freed", spaceFreed)
            putLong("duration", duration)
        }
        analytics.logEvent(Constants.ANALYTICS_EVENT_CLEAN_COMPLETE, bundle)
    }
    
    /**
     * Log deep clean viewed event
     */
    fun logDeepCleanViewed() {
        analytics.logEvent(Constants.ANALYTICS_EVENT_DEEP_CLEAN_VIEWED, null)
    }
    
    /**
     * Log ad rewarded event
     */
    fun logAdRewarded() {
        analytics.logEvent(Constants.ANALYTICS_EVENT_AD_REWARDED, null)
    }
}
