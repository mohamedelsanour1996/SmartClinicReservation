package com.example.smartclinicreservation;

import android.app.Application;

import com.backendless.Backendless;

public class DoctorApplication extends Application {
    public static final String APPLICATION_ID = "";//from back end less
    public static final String API_KEY = "";//from back end less
    public static final String SERVER_URL = "";//from back end less

    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.setUrl( SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );

    }
}
