package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.jdelorenzo.jokedisplaylibrary.JokeDisplayActivity;
import com.jdelorenzo.jokeendpoint.myApi.MyApi;

import java.io.IOException;

//task that sends a request to the Cloud Endpoint backednAPI
//https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
public class RetrieveJokeTask extends AsyncTask<Context, Void, String> {
    private OnJokeLoaded listener;
    private static MyApi myApiService = null;
    private Context mContext;

    public RetrieveJokeTask(OnJokeLoaded listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(Context... params) {
        mContext = params[0];
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl(mContext.getString(R.string.localhost_endpoint_address))
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            //turn off compression when running against local devappserver
                            request.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }
        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String jokeText) {
        super.onPostExecute(jokeText);
        listener.onJokeLoaded(jokeText);
    }
}
