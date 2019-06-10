package com.example.smartclinicreservation.patient;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.backendless.Backendless;
import com.example.smartclinicreservation.ChatActivity;
import com.example.smartclinicreservation.R;
import com.example.smartclinicreservation.doctor.DoctorInformation;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;


public class HomeScreenPatient extends AppCompatActivity {
    Button btnChat, btnDoctor, btnBook;
    String name = Backendless.UserService.CurrentUser().getProperty("name").toString();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home_screen);
        btnChat = findViewById(R.id.btnChat);
        btnDoctor = findViewById(R.id.btnDoc);
        btnBook = findViewById(R.id.btnBook);
        OneSignal.startInit(this).init();
        OneSignal.setSubscription(true);
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                FirebaseDatabase.getInstance().getReference().child("users").child(name).child("notificationKey").setValue(userId);
            }
        });
        OneSignal.setInFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification);
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenPatient.this, Booking.class));
            }
        });

        btnDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenPatient.this, DoctorInformation.class));
            }
        });


        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenPatient.this, ChatActivity.class));
            }
        });

    }


}
