package com.ub.interstitialadmanager.interstitialAd

import com.google.android.gms.ads.interstitial.InterstitialAd

object InterstitialHelper {

    //ad gap between two ads is 5 seconds.
    var interstitialAdCapping: Long = 15
    var lastAdTime: Long = 0
    var isAdLoaded: Boolean = false
    var mInterstitialAd: InterstitialAd? = null
}