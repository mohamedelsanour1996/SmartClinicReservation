package com.example.smartclinicreservation.doctor;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.example.smartclinicreservation.SendNotification;
import com.example.smartclinicreservation.adapter.PatientListAdapter;
import com.example.smartclinicreservation.R;
import com.example.smartclinicreservation.model.ChatMessage;
import com.example.smartclinicreservation.model.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorChatActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<String>Patients;
    FirebaseDatabase db;
    PatientListAdapter adapter;
    FloatingActionButton send;
    EditText edittext_chatbox;
    String name = Backendless.UserService.CurrentUser().getProperty("name").toString().trim();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_doctor);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        send=findViewById(R.id.button_chatbox_send);
        edittext_chatbox = findViewById(R.id.edittext_chatbox);
        Patients=new ArrayList<>();
        db=FirebaseDatabase.getInstance();
        recyclerView=findViewById(R.id.Patients);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendforall();
            }
        });

            displayChatMessage();



    }

    private void sendforall() {
        if(!edittext_chatbox.getText().toString().equals("")){
        for (final String s:Patients) {
            FirebaseDatabase.getInstance().getReference().child("users").child(s).child("messages").push().setValue(new ChatMessage(edittext_chatbox.getText().toString(), name, ""));
            edittext_chatbox.setText("");
        }
        }
        else {
            Toast.makeText(this, "plz write a message", Toast.LENGTH_SHORT).show();
        }
    }

    private void displayChatMessage() {
        Patients.clear();
        db.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Patients.clear();
                for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                    String name=snapshot.getKey();
                    Patients.add(name);

                }

                adapter = new PatientListAdapter(DoctorChatActivity.this,Patients);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}