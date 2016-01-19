package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.JokeTeller;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.jdelorenzo.jokedisplaylibrary.JokeDisplayActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

//@see <ahref="https://developers.google.com/admob/android/interstitial">Interstitial Docs</a>
public class MainActivity extends AppCompatActivity implements OnJokeLoaded {

    @Bind (R.id.progress_bar) android.support.v4.widget.ContentLoadingProgressBar loadingProgressBar;
    InterstitialAd mInterstitialAd;
    Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();
                requestNewInterstitialAd();
            }
        });
        requestNewInterstitialAd();
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
        JokeTeller jokeTeller = new JokeTeller();
        //the interstitial ad will cover the loading progress bar, so only show it if the ad
        //is not loaded.
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
        else {
            loadingProgressBar.show();
        }
        new RetrieveJokeTask(this).execute(getString(R.string.deployed_endpoint_address));
    }

    public void onJokeLoaded(String jokeText) {
        loadingProgressBar.hide();
        if (jokeText.equals("")) {
            if (mToast != null) {
                mToast.cancel();
            }
            mToast = Toast.makeText(this, getString(R.string.joke_retrieval_failure_msg), Toast.LENGTH_SHORT);
            mToast.show();
        }
        Intent intent = new Intent(this, JokeDisplayActivity.class);
        intent.putExtra(JokeDisplayActivity.ARG_JOKETEXT, jokeText);
        startActivity(intent);
    }

    private void requestNewInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);
    }
}
