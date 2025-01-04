package com.ub.interstitialadmanager.interstitialAd

object InterstitialHelper {

    //ad gap between two ads is 5 seconds.
    var interstitialAdCapping: Long = 15
    var lastAdTime: Long = 0
    var isAdLoaded: Boolean = false
}