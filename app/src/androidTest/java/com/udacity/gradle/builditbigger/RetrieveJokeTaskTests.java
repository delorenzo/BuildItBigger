package com.udacity.gradle.builditbigger;

import android.test.InstrumentationTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

//runs async task on UI thread to verify results.
//see https://gist.github.com/he9lin/2195897
public class RetrieveJokeTaskTests extends InstrumentationTestCase {
    private static String jokeTextResult;
    private static boolean called;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        called = false;
        jokeTextResult = null;
    }

    public final void testRetrieveJokeTask() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

        //execute the async task on the UI thread
        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                new RetrieveJokeTask(new OnJokeLoaded() {
                    @Override
                    public void onJokeLoaded(String jokeText) {
                        called = true;
                        jokeTextResult = jokeText;
                    }
                }) {
                    @Override
                    protected void onPostExecute(String jokeText) {
                        super.onPostExecute(jokeText);
                        signal.countDown();
                    }
                }.execute();
            }
        });

        signal.await(10, TimeUnit.SECONDS);
        assertTrue(called);
        assertNotNull(jokeTextResult);
        Boolean stringIsEmpty = jokeTextResult.equals("");
        assertFalse(stringIsEmpty);
    }
}
