package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.JavaJokesCorey;
import com.example.cmbarnett.androidjokelib.*;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A placeholder fragment containing a simple view.
 * Using http://jakewharton.github.io/butterknife/
 */
public class MainActivityFragment extends Fragment {
    @Bind(R.id.btnTellJoke)
    Button btnTellJoke;

    @Bind(R.id.tvJoke)
    TextView tvJoke;

    @Bind(R.id.btnTellAndroidJoke)
    Button btnTellAndroidJoke;


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
    public void showAndroidJoke(){
        AndroidJoke androidJoke = new AndroidJoke();
        Intent intent = new Intent(getContext(), com.example.cmbarnett.androidjokelib.MainActivity.class);
        intent.putExtra("joke",androidJoke.tellAndroidJoke());
        getContext().startActivity(intent);
    }


}
