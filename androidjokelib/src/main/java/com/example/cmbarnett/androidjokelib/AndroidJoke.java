package com.example.cmbarnett.androidjokelib;

/**
 * Created by cmbar on 11/22/2015.
 */
public class AndroidJoke {
    //Joke from https://jaxenter.com/top-viral-java-jokes-2014-113040.html
    String strJoke = "Why do Java programmers wear glasses? Because they can't see sharp!";
    public String tellAndroidJoke() {
        return strJoke;
    }

    public void setAndroidJoke(String strPassedJoke){
        strJoke = strPassedJoke;
    }
}
