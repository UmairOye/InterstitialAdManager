# InterstitialAdManager Class

`InterstitialAdManager` is a utility class designed to manage interstitial ads in Android applications using Google AdMob. It handles the loading, showing, and capping of interstitial ads, ensuring they are shown only after a defined time interval to avoid overwhelming the user.

## Features

- **Load Interstitial Ads**: Automatically loads interstitial ads from AdMob.
- **Display Interstitial Ads**: Shows the interstitial ads at the appropriate moments.
- **Ad Capping**: Prevents displaying interstitial ads too frequently by enforcing a time gap between consecutive ads.
- **Network Availability Check**: Ensures that the device has a valid network connection before attempting to load ads.
- **Loading Dialog**: Displays a loading dialog while the ad is being prepared.

## Class Overview

### Properties

- **mInterstitialAd**: Holds the loaded interstitial ad.
- **adRequest**: The request used to load an ad from AdMob.
- **TAG**: Log tag for debugging purposes.
- **adLoadingDialog**: A custom loading dialog that shows while the interstitial ad is being prepared.

### Functions

#### 1. `loadInterstitialAd(context: Context)`
   - **Purpose**: Loads an interstitial ad from AdMob if one is not already loaded and the device has a valid network connection.
   - **Parameters**: 
     - `context`: The context of the calling activity or application.
   - **Usage**:
     ```kotlin
     interstitialAdManager.loadInterstitialAd(context)
     ```

#### 2. `showInterstitialAd(context: Activity)`
   - **Purpose**: Displays the interstitial ad if available, and shows a loading dialog while the ad is being prepared.
   - **Parameters**:
     - `context`: The activity context that will host the ad.
   - **Usage**:
     ```kotlin
     interstitialAdManager.showInterstitialAd(this)
     ```

#### 3. `adCapping()`
   - **Purpose**: Ensures that the ad is only shown after a defined time gap (capping) to prevent showing too many ads in a short period.
   - **Usage**: This function is used internally to check if the minimum time between ads has passed.

#### 4. `isNetworkAvailable(context: Context)`
   - **Purpose**: Checks if the device has an active network connection.
   - **Parameters**:
     - `context`: The context to get the system’s network service.
   - **Usage**:
     ```kotlin
     interstitialAdManager.isNetworkAvailable(context)
     ```

---

## Usage Example

### Initialize and Load Ad

Before showing an ad, you need to load it. You can load the ad by calling the `loadInterstitialAd()` function.

```kotlin
val interstitialAdManager = InterstitialAdManager()
interstitialAdManager.loadInterstitialAd(context)
