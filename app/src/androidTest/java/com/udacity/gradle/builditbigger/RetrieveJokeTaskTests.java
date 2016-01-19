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
    final String localEndpoint = "http://10.0.2.2:8080/_ah/api/";
    final String testEndpoint = "https://jokeendpoint-1194.appspot.com/_ah/api/";

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
        new RetrieveJokeTask(this).execute(testEndpoint);
        signal.await(30, TimeUnit.SECONDS);
        assertTrue(called);
        assertNotNull(jokeTextResult);
        Boolean stringIsEmpty = jokeTextResult.equals("");
        assertFalse(stringIsEmpty);
    }

    //this test will only pass if the local dev server is running!
    public void testRetrieveJokeTaskLocal() throws InterruptedException {
        new RetrieveJokeTask(this).executeLocal(localEndpoint);
        signal.await(30, TimeUnit.SECONDS);
        assertTrue(called);
        assertNotNull(jokeTextResult);
        Boolean stringIsEmpty = jokeTextResult.equals("");
        assertFalse(stringIsEmpty);
    }



}
