package com.udacity.gradle.builditbigger;

import android.test.InstrumentationTestCase;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

//runs async task on UI thread to verify results.
//see https://gist.github.com/he9lin/2195897
public class RetrieveJokeTaskTests extends InstrumentationTestCase implements OnJokeLoaded {
    private static String jokeTextResult;
    private static boolean called;
    private CountDownLatch signal;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        signal = new CountDownLatch(1);
    }

    @Override
    public void onJokeLoaded(String jokeText) {
        called = true;
        jokeTextResult = jokeText;
        signal.countDown();
    }

    public void testRetrieveJokeTask() throws InterruptedException {
        new RetrieveJokeTask(this).execute("https://jokeendpoint-1194.appspot.com/_ah/api/");
        signal.await(30, TimeUnit.SECONDS);
        assertTrue(called);
        assertNotNull(jokeTextResult);
        Boolean stringIsEmpty = jokeTextResult.equals("");
        assertFalse(stringIsEmpty);
    }
}
