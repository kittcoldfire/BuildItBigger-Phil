
package com.udacity.gradle.builditbigger;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.etechtour.builditbigger.backend.myApi.MyApi;
import com.etechtour.jokeactivity.JokeActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;


public class MainActivity extends ActionBarActivity {

    private MainReceiver reciever;
    private ProgressBar loading;
    private EndpointsAsyncTask async;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reciever = new MainReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(EndpointsAsyncTask.ENDPOINT_INTENT);
        intentFilter.addAction(AdsInterface.ADS_FINISH);
        registerReceiver(reciever, intentFilter);

        loading = (ProgressBar) findViewById(R.id.loading);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(reciever);
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

    public void tellJoke(View view) {
        //Intent intent = new Intent(this, JokeActivity.class);
//        Jokes jokeSource = new Jokes();
//        String joke = jokeSource.getJoke(i);
//        intent.putExtra(JokeActivity.JOKE_KEY, joke);
        //startActivity(intent);

        //Toast.makeText(this, joke, Toast.LENGTH_LONG).show();

        //Show our progress bar so they know its loading
        loading.setVisibility(View.VISIBLE);

        //get a reference to our fragment
        MainActivityFragment f =
                (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        //call to load the ad, and no matter what if its free or paid, we'll go to our Async task
        f.showInterstitialAd(this);
    }

    private class MainReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                if (intent.getAction().equals(EndpointsAsyncTask.ENDPOINT_INTENT)) {
                    //Once we get the response, get rid of the progress dialog
                    loading.setVisibility(View.GONE);

                    Intent activityIntent = new Intent(MainActivity.this, JokeActivity.class);
                    activityIntent.putExtra(JokeActivity.JOKE_KEY,
                            intent.getStringExtra(EndpointsAsyncTask.ENDPOINT_RESULT));
                    startActivity(activityIntent);
                } else if(intent.getAction().equals(AdsInterface.ADS_FINISH)) {
                    //They just saw an Ad, now lets launch our joke!
                    //Make the progress bar visible while it's loading
                    loading.setVisibility(View.VISIBLE);
                    async = new EndpointsAsyncTask();
                    async.execute(MainActivity.this);
                }
            }
        }
    }
}

class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    public static final String ENDPOINT_INTENT = "com.etechtour.backend.intent.endpoint";
    public static final String ENDPOINT_RESULT = "com.etechtour.backend.intent.response";

    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://black-function-112915.appspot.com/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });

            myApiService = builder.build();
        }

        context = params[0];

        try {
            return myApiService.tellJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(ENDPOINT_INTENT);
        intent.putExtra(ENDPOINT_RESULT, result);
        context.sendBroadcast(intent);
    }
}
