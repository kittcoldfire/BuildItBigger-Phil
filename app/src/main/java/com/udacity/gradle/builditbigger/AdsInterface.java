package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.view.View;

/**
 * Created by Phil on 11/16/2015.
 * Make this interface so that a class from both flavors can implement to check for ads,
 * paid version won't have ads, free version will get it and find it in the xml and than can load them
 */
public interface AdsInterface {

    public final String ADS_FINISH = "com.etechtour.ads.interstitial.finish";

    void loadAds(View v);
    void initInterstitialAds(Context context);
    void showInterstitialAd(Context context);
}
