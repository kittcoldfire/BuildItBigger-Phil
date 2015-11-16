package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Phil on 11/16/2015.
 *
 */
public class FlavorsFeatures implements AdsInterface {

    InterstitialAd mInterstitialAd;

    @Override
    public void loadAds(View v) {

        //Because we're in the free flavor, we should find the ad view so we can populate the ads
        AdView mAdView = (AdView) v.findViewById(R.id.adView);

        //Just to be safe, don't try and load ads unless the view was found
        if(mAdView != null) {
            // Create an ad request. Check logcat output for the hashed device ID to
            // get test ads on a physical device. e.g.
            // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .build();
            mAdView.loadAd(adRequest);
        }
    }

    public void initInterstitialAds(final Context context) {
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                closeInterstitial(context);
            }
        });

        requestNewInterstitial();
    }

    public void showInterstitialAd(Context context) {
        //If an AD is ready, show it to the user
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            //If the ad isn't ready, forget it and move onto the next activity
            closeInterstitial(context);
        }
    }

    //load a new ad in the background for next ad
    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }

    private void closeInterstitial(Context context){
        //We're done with the ad, send a broadcast to start the next activity
        Intent intent = new Intent(AdsInterface.ADS_FINISH);
        context.sendBroadcast(intent);
    }
}
