package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    //Our flavor interface
    private FlavorsFeatures ff;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);


        //instantiate the interface
        ff = new FlavorsFeatures();
        //whether we are paid or free, this will check if the layout has the ads or not
        ff.loadAds(root);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //init our interstitial ads
        ff.initInterstitialAds(getActivity());
    }

    public void showInterstitialAd(Context context) {
        //no matter whether we are in paid or free version
        ff.showInterstitialAd(context);
    }
}
