package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.util.concurrent.CountDownLatch;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    CountDownLatch signal = null;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    public void testEndopointsAsyncTask() throws InterruptedException {
        EndpointsAsyncTask task = new EndpointsAsyncTask();
        MainActivity activity = null;
        task.setListener(new EndpointsAsyncTask.EndpointsAsyncTaskListener() {
            @Override
            public void onComplete(String result) {
                assertFalse(result.equals(EndpointsAsyncTask.ASYNCTASK_NOT_OK));
                signal.countDown();
            }
        }).execute(activity);
        signal.await();



    }
}