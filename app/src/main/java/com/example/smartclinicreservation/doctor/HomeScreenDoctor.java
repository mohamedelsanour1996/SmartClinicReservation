package com.example.smartclinicreservation.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.backendless.Backendless;
import com.example.smartclinicreservation.R;
import com.google.firebase.database.FirebaseDatabase;
import com.onesignal.OneSignal;

public class HomeScreenDoctor extends AppCompatActivity {
    Button btnChat,btnBook;
    String name = Backendless.UserService.CurrentUser().getProperty("name").toString();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home_screen);
        OneSignal.startInit(this).init();
        OneSignal.setSubscription(true);
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                FirebaseDatabase.getInstance().getReference().child("doctornotificationKey").child("notificationKey").setValue(userId);
            }
        });
        OneSignal.setInFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification);


        btnChat=findViewById(R.id.btnChat);
        btnBook=findViewById(R.id.btnBook);

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(HomeScreenDoctor.this, DoctorBookedTime.class));
            }
        });



        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeScreenDoctor.this, DoctorChatActivity.class));
            }
        });

    }





}

