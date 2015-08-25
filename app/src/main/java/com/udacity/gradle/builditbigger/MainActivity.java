package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.rukiasoft.rulerapps.myjokedisplay.JokeDisplayActivity;


public class MainActivity extends AppCompatActivity {

    protected SwipeRefreshLayout refreshLayout;
    private Boolean showing = false;

    InterstitialAd mInterstitialAd;
    MainActivity activity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = this;
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id));

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                showRefreshLayoutSwipeProgress();
                new EndpointsAsyncTask().execute(activity);
            }
        });

        requestNewInterstitial();


    }

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view){
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }


    public void showJoke(String result) {
        Intent intent = new Intent(this, JokeDisplayActivity.class);
        intent.putExtra(JokeDisplayActivity.JOKE_AS_INTENT, result);
        startActivity(intent);
    }

    public void setRefreshLayout(SwipeRefreshLayout _refreshLayout){
        refreshLayout = _refreshLayout;
        if (refreshLayout == null) {
            return;
        }
        //configure swipeRefreshLayout
        setRefreshLayoutColorScheme(getResources().getColor(R.color.color_scheme_1_1),
                getResources().getColor(R.color.color_scheme_1_2),
                getResources().getColor(R.color.color_scheme_1_3),
                getResources().getColor(R.color.color_scheme_1_4));
    }

    /**
     * It shows the SwipeRefreshLayout progress
     */
    public void showRefreshLayoutSwipeProgress() {
        if (refreshLayout == null) {
            return;
        }
        refreshLayout.setRefreshing(true);
        showing = true;
    }



    /**
     * It shows the SwipeRefreshLayout progress
     */
    public void hideRefreshLayoutSwipeProgress() {
        if (refreshLayout == null) {
            return;
        }
        refreshLayout.setRefreshing(false);
        showing = false;
    }

    /**
     * Enables swipe gesture
     */
    public void enableRefreshLayoutSwipe() {
        if (refreshLayout == null) {
            return;
        }
        refreshLayout.setEnabled(true);
    }

    /**
     * Disables swipe gesture. It prevents manual gestures but keeps the option tu show
     * refreshing programatically.
     */
    public  void disableRefreshLayoutSwipe() {
        if (refreshLayout == null) {
            return;
        }
        refreshLayout.setEnabled(false);
    }

    /**
     * Set colors of refreshlayout
     */
    private void setRefreshLayoutColorScheme(int colorRes1, int colorRes2, int colorRes3, int colorRes4) {
        if (refreshLayout == null) {
            return;
        }
        refreshLayout.setColorSchemeColors(colorRes1, colorRes2, colorRes3, colorRes4);
    }
}
