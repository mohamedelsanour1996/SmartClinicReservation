package com.example.smartclinicreservation.doctor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.smartclinicreservation.patient.Booking;
import com.example.smartclinicreservation.R;

public class DoctorInformation extends AppCompatActivity {
Button book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_information);
        book=findViewById(R.id.book);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorInformation.this, Booking.class));
            }
        });
    }
}
