package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v4.util.Pair;
import android.test.ApplicationTestCase;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.cmbar.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
//http://marksunghunpark.blogspot.ru/2015/05/how-to-test-asynctask-in-android.html
//https://gist.github.com/he9lin/2195897
public class ApplicationTest extends ApplicationTestCase<Application> {
    final String LOG_TAG = "CMB";
    //String mJsonString = null;
    String strJoke;
    Exception mError = null;
    CountDownLatch signal = null;
    Boolean blCalled = null;

    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        blCalled = false;
        signal = new CountDownLatch(1);
    }

    @Override
    protected void tearDown() throws Exception {
        signal.countDown();
    }

    public void testGetJoke() throws InterruptedException {


        assertNull(mError);

        new EndpointsAsyncTask().execute(new Pair<Context, String>(getContext(), ""));
        signal.await(10, TimeUnit.SECONDS);
        assertTrue(blCalled);
    }

    class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
        private MyApi myApiService = null;
        private Context context;

        @Override
        protected String doInBackground(Pair<Context, String>... params) {
            if (myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                        .setRootUrl("https://coreyudacityjoke.appspot.com/_ah/api/");
                myApiService = builder.build();
            }
            context = params[0].first;

            try {
                //return myApiService.sayHi(name).execute().getData();
                return myApiService.tellJoke().execute().getData();
            } catch (IOException e) {
                Log.e(LOG_TAG, e.toString());
                return String.format("It's not funny, there's an error: %s", e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            //Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            blCalled = true;
            signal.countDown();//added 11/30/15
        }


    }
}


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
/*
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
}*/
