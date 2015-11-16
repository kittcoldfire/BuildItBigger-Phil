package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * Created by Phil on 11/16/2015.
 *
 */
public class FlavorsFeatures implements AdsInterface {

    @Override
    public void loadAds(View v) {
        //Because we're in the paid version, we have no ads so won't bother doing anything here
    }

    @Override
    public void initInterstitialAds(Context context) {
        //Don't need to do anything here for the paid version
    }

    @Override
    public void showInterstitialAd(Context context) {
        //Paid version, just skip all this!
        closeInterstitial(context);
    }

    private void closeInterstitial(Context context){
        //Because we don't have any ads, just send the broadcast to start the jokes!
        Intent intent = new Intent(AdsInterface.ADS_FINISH);
        context.sendBroadcast(intent);
    }
}
