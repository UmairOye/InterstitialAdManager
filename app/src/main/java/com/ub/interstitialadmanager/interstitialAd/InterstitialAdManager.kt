package com.ub.interstitialadmanager.interstitialAd

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.ub.interstitialadmanager.R
import com.ub.interstitialadmanager.appDialog.AdLoadingDialog
import com.ub.interstitialadmanager.interstitialAd.InterstitialHelper.isAdLoaded
import com.ub.interstitialadmanager.interstitialAd.InterstitialHelper.lastAdTime
import java.util.Locale
import java.util.concurrent.TimeUnit

class InterstitialAdManager {
    private val adRequest = AdRequest.Builder().build()
    private var mInterstitialAd: InterstitialAd? = null
    private val TAG = "AdmobAd"
    private val adLoadingDialog = AdLoadingDialog()


    //load ad
    fun loadInterstitialAd(context: Context) {
        if(isAdLoaded.not() && mInterstitialAd==null && isNetworkAvailable(context)){
            InterstitialAd.load(context,context.getString(R.string.interstitial_ad_it), adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, "InterstitialAd failed to load with error: ${adError.message}")
                    mInterstitialAd = null
                    isAdLoaded = false
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded with id: ${interstitialAd.adUnitId}")
                    mInterstitialAd = interstitialAd
                    isAdLoaded = true
                }
            })
        }else{
            Log.d(TAG, "Unable to load ad, please try again later. lastAdTime -> $lastAdTime")
        }
    }


    fun showInterstitialAd(context: Activity){
        //you can add further checks of premium is true or app open is showing,etc,etc

        if(mInterstitialAd!=null && adCapping()){
           adLoadingDialog.showLoadingDialog(context, 900){
               mInterstitialAd!!.show(context)
           }

            mInterstitialAd!!.fullScreenContentCallback =  object: FullScreenContentCallback(){
                override fun onAdDismissedFullScreenContent() {
                    Log.d(TAG, "InterstitialAd was dismissed.")
                    loadInterstitialAd(context)
                }


                override fun onAdImpression() {
                    super.onAdImpression()
                    mInterstitialAd = null
                    isAdLoaded = false
                    Log.d(TAG, "InterstitialAd impression received.")
                    loadInterstitialAd(context)
                }

                override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                    super.onAdFailedToShowFullScreenContent(p0)
                    isAdLoaded = false
                    Log.d(TAG, "InterstitialAd failed to show with error: ${p0.message}")
                }
            }


        }else{
            loadInterstitialAd(context)
            Log.d(TAG, "ad in not available right now.")
        }
    }


    private fun adCapping(): Boolean{
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastAdTime > InterstitialHelper.interstitialAdCapping*1000) {
            lastAdTime = currentTime
            return true
        }else
        {
            return false
        }
    }


    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}