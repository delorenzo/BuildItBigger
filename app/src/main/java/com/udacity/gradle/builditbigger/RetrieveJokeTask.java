package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.jdelorenzo.jokeendpoint.myApi.MyApi;

import java.io.IOException;

//task that sends a request to the Cloud Endpoint backend API
//https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
public class RetrieveJokeTask extends AsyncTask<String, Void, String> {
    private OnJokeLoaded listener;
    private Boolean localEndpoint = false;
    private static MyApi myApiService = null;

    public RetrieveJokeTask(OnJokeLoaded listener) {
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        if (params.length == 0) return "";
        String endpointURL = params[0];
        if (myApiService == null) {
            MyApi.Builder builder;
            if (localEndpoint) {
                builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl(endpointURL)
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                                //turn off compression when running against local devappserver
                                request.setDisableGZipContent(true);
                            }
                        });
            }
            else {
                builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl(endpointURL);
            }
            myApiService = builder.build();
        }
        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    protected void executeLocal(String... params) {
        localEndpoint = true;
        execute(params);
    }

    @Override
    protected void onPostExecute(String jokeText) {
        super.onPostExecute(jokeText);
        listener.onJokeLoaded(jokeText);
    }
}
