package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.JavaJokesCorey;
import com.example.cmbar.myapplication.backend.myApi.MyApi;
import com.example.cmbarnett.androidjokelib.*;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A placeholder fragment containing a simple view.
 * Using http://jakewharton.github.io/butterknife/
 * https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
 */
public class MainActivityFragment extends Fragment {
    final String LOG_TAG = "CMB";
    @Bind(R.id.btnTellJoke)
    Button btnTellJoke;

    @Bind(R.id.tvJoke)
    TextView tvJoke;

    @Bind(R.id.btnTellAndroidJoke)
    Button btnTellAndroidJoke;

    @Bind(R.id.btnTellGCEJoke)
    Button btnTellGCEJoke;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, root);

        AdView mAdView = (AdView) root.findViewById(R.id.adView);
        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return root;
    }

    @OnClick(R.id.btnTellJoke)
    public void showJoke() {
        JavaJokesCorey joke = new JavaJokesCorey();
        tvJoke.setText(joke.tellJavaJoke());

        Toast.makeText(getActivity(), joke.tellJavaJoke(), Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btnTellAndroidJoke)
    public void showAndroidJoke() {
        AndroidJoke androidJoke = new AndroidJoke();
        Intent intent = new Intent(getContext(), com.example.cmbarnett.androidjokelib.MainActivity.class);
        intent.putExtra("joke", androidJoke.tellAndroidJoke());
        getContext().startActivity(intent);
    }

    @OnClick(R.id.btnTellGCEJoke)
    public void showGCEJoke() {
        btnTellGCEJoke.setEnabled(false);//disable to prevent multiple clicks
        new EndpointsAsyncTask().execute(new Pair<Context, String>(getContext(), "Manfred"));
    }

    //https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
    class EndpointsAsyncTask extends AsyncTask<Pair<Context, String>, Void, String> {
        private MyApi myApiService = null;
        private Context context;

        @Override
        protected String doInBackground(Pair<Context, String>... params) {
            if (myApiService == null) {  // Only do this once
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        // options for running against local devappserver
                        // - 10.0.2.2 is localhost's IP address in Android emulator
                        // - turn off compression when running against local devappserver
                        //.setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        //changed to localhost 11/28/15
                        .setRootUrl("http://localhost:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                                abstractGoogleClientRequest.setDisableGZipContent(true);
                            }
                        });
                // end options for devappserver

                myApiService = builder.build();
            }

            context = params[0].first;
            String name = params[0].second;

            try {
                return myApiService.sayHi(name).execute().getData();
            } catch (IOException e) {
                Log.e(LOG_TAG,e.toString());
                return String.format("It's not funny, there's an error: %s", e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            btnTellGCEJoke.setEnabled(true);//disabled previously to prevent multiple clicks
        }
    }


}
