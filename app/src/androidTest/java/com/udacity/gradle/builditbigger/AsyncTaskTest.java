package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.test.mock.MockContext;
import android.util.Log;

import java.util.concurrent.CountDownLatch;

/**
 * Created by Phil on 11/15/2015.
 */
public class AsyncTaskTest extends AndroidTestCase {
    //We need a countdown to wait on the thread for the AsyncTask to fire off it's methods while testing
    private final CountDownLatch timer = new CountDownLatch(1);

    private class TestAsyncTask extends EndpointsAsyncTask {
        @Override
        protected void onPostExecute(String result) {
            assertTrue("Result is empty, something went wrong!", result != null && result.length() > 0);
            Log.d("AsyncTaskTest", result);
            timer.countDown();
        }
    }

    public void testAsyncMethod() throws Throwable {
        final TestAsyncTask asyncTask = new TestAsyncTask();

        asyncTask.execute(new MockContext());
        //Wait for the AsyncTask to finish so we can catch the output
        timer.await();
    }
}
