package com.example.smartclinicreservation;

import android.app.Application;

import com.backendless.Backendless;

public class DoctorApplication extends Application {
    public static final String APPLICATION_ID = "5843AE4B-E145-A5AD-FF05-D43A23EC4000";
    public static final String API_KEY = "FC6E5CDC-6298-4B85-FF76-16292B3D0200";
    public static final String SERVER_URL = "https://api.backendless.com";

    @Override
    public void onCreate() {
        super.onCreate();
        Backendless.setUrl( SERVER_URL );
        Backendless.initApp( getApplicationContext(),
                APPLICATION_ID,
                API_KEY );

    }
}
