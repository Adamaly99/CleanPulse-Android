package com.cleanpulse.ads

import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.cleanpulse.Constants

/**
 * Manages AdMob advertisements (Banner, Interstitial, Rewarded)
 */
class AdManager(private val context: Context) {
    
    private var interstitialAd: InterstitialAd? = null
    private var rewardedAd: RewardedAd? = null
    
    private var onRewardedCallback: (() -> Unit)? = null
    
    companion object {
        private const val TAG = "AdManager"
    }
    
    init {
        // Initialize Google Mobile Ads SDK
        MobileAds.initialize(context)
    }
    
    // ========== INTERSTITIAL ADS ==========
    
    /**
     * Load interstitial ad
     */
    fun loadInterstitialAd() {
        val adRequest = AdRequest.Builder().build()
        
        InterstitialAd.load(
            context,
            Constants.ADMOB_INTERSTITIAL_AD_ID,
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                    Log.d(TAG, "Interstitial ad loaded")
                }
                
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    interstitialAd = null
                    Log.e(TAG, "Failed to load interstitial ad: ${adError.message}")
                }
            }
        )
    }
    
    /**
     * Show interstitial ad
     */
    fun showInterstitialAd(onDismissed: () -> Unit = {}) {
        if (interstitialAd != null) {
            interstitialAd?.fullScreenContentCallback = object : com.google.android.gms.ads.FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Interstitial ad dismissed")
                    interstitialAd = null
                    onDismissed()
                    // Reload for next time
                    loadInterstitialAd()
                }
                
                override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                    Log.e(TAG, "Failed to show interstitial ad: ${adError.message}")
                    interstitialAd = null
                }
                
                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Interstitial ad showed")
                }
            }
            
            interstitialAd?.show(null)  // Pass activity reference if available
        } else {
            Log.w(TAG, "Interstitial ad not loaded yet")
            onDismissed()
        }
    }
    
    /**
     * Check if interstitial ad is loaded
     */
    fun isInterstitialAdLoaded(): Boolean = interstitialAd != null
    
    // ========== REWARDED ADS ==========
    
    /**
     * Load rewarded ad
     */
    fun loadRewardedAd() {
        val adRequest = AdRequest.Builder().build()
        
        RewardedAd.load(
            context,
            Constants.ADMOB_REWARDED_AD_ID,
            adRequest,
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                    Log.d(TAG, "Rewarded ad loaded")
                }
                
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    rewardedAd = null
                    Log.e(TAG, "Failed to load rewarded ad: ${adError.message}")
                }
            }
        )
    }
    
    /**
     * Show rewarded ad
     */
    fun showRewardedAd(onReward: () -> Unit, onDismissed: () -> Unit = {}) {
        if (rewardedAd != null) {
            onRewardedCallback = onReward
            
            rewardedAd?.fullScreenContentCallback = object : com.google.android.gms.ads.FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "Rewarded ad dismissed")
                    rewardedAd = null
                    onDismissed()
                    // Reload for next time
                    loadRewardedAd()
                }
                
                override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                    Log.e(TAG, "Failed to show rewarded ad: ${adError.message}")
                    rewardedAd = null
                }
                
                override fun onAdShowedFullScreenContent() {
                    Log.d(TAG, "Rewarded ad showed")
                }
            }
            
            rewardedAd?.show(null) { reward ->  // Pass activity reference if available
                Log.d(TAG, "User earned reward: ${reward.amount} ${reward.type}")
                onRewardedCallback?.invoke()
            }
        } else {
            Log.w(TAG, "Rewarded ad not loaded yet")
            onDismissed()
        }
    }
    
    /**
     * Check if rewarded ad is loaded
     */
    fun isRewardedAdLoaded(): Boolean = rewardedAd != null
    
    // ========== BANNER ADS ==========
    
    /**
     * Create banner ad request
     * Note: Banner ads are typically displayed in XML layouts
     */
    fun getBannerAdRequest(): AdRequest {
        return AdRequest.Builder().build()
    }
    
    /**
     * Get banner ad unit ID
     */
    fun getBannerAdUnitId(): String = Constants.ADMOB_BANNER_AD_ID
}
