package com.udacity.gradle.builditbigger;

import android.test.InstrumentationTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

//runs async task on UI thread to verify results.
//see https://gist.github.com/he9lin/2195897
public class RetrieveJokeTaskTests extends InstrumentationTestCase {
    private static String jokeTextResult;
    private static boolean called;

    public final void testRetrieveJokeTask() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

        final OnJokeLoaded mListener = new OnJokeLoaded() {
            @Override
            public void onJokeLoaded(String jokeText) {
                called = true;
                jokeTextResult = jokeText;
                signal.countDown();
            }
        };

        //execute the async task on the UI thread
        final RetrieveJokeTask myTask = new RetrieveJokeTask(mListener) {
            @Override
            protected void onPostExecute(String jokeText) {
                super.onPostExecute(jokeText);
                mListener.onJokeLoaded(jokeText);
                signal.countDown();
            }
        };
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                myTask.execute(this);
            }

        });

        signal.await(30, TimeUnit.SECONDS);
        assertTrue(called);
        assertNotNull(jokeTextResult);
        Boolean stringIsEmpty = jokeTextResult.equals("");
        assertFalse(stringIsEmpty);
    }
}
