package com.example.smartclinicreservation;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.backendless.Backendless;
import com.example.smartclinicreservation.adapter.ChatMessageAdapter;
import com.example.smartclinicreservation.model.ChatMessage;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ChatActivity extends AppCompatActivity {

    private static int SIGN_IN_REQUEST_CODE = 1;
    EditText edittext_chatbox;
    FloatingActionButton button_chatbox_send,button_chatbox_send_image;
    RecyclerView mMessageRecycler;
    ChatMessageAdapter adapter;
    List<ChatMessage>ChatMessageList=new ArrayList<>();
    String name = Backendless.UserService.CurrentUser().getProperty("name").toString().trim();
    String phone = Backendless.UserService.CurrentUser().getProperty("phone").toString().trim();
    String patient_name;
    String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_patient);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        if (phone.equals("01096427890")){
            Intent i=getIntent();
            patient_name=i.getStringExtra("name");
            FirebaseDatabase.getInstance().getReference().child("users").child(patient_name).child("notificationKey").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    key=dataSnapshot.getValue(String.class);
                    Log.i("key", "onDataChange: "+key);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else {
            patient_name=name;
            FirebaseDatabase.getInstance().getReference().child("doctornotificationKey").child("notificationKey").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    key=dataSnapshot.getValue(String.class);
                    Log.i("key", "onDataChange: "+key);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

        mMessageRecycler = findViewById(R.id.reyclerview_message_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(ChatActivity.this);
        layoutManager.setStackFromEnd(true);
        mMessageRecycler.setLayoutManager(layoutManager);
        edittext_chatbox = findViewById(R.id.edittext_chatbox);
        button_chatbox_send = findViewById(R.id.button_chatbox_send);
        button_chatbox_send_image = findViewById(R.id.button_chatbox_send_image);
        displayChatMessage();
        button_chatbox_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMesaaage();

            }
        });
        button_chatbox_send_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              sendImage();

            }
        });

    }

    private void sendImage() {
        Intent galleryIntent=new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(galleryIntent,1);
    }

    private void sendMesaaage() {

        FirebaseDatabase.getInstance().getReference().child("users").child(patient_name).child("messages").push().setValue(new ChatMessage(edittext_chatbox.getText().toString(), name,""));
        new SendNotification(edittext_chatbox.getText().toString(),name,key);
        edittext_chatbox.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1&resultCode==RESULT_OK){
            Uri imageUri=data.getData();
            FirebaseStorage.getInstance().getReference().child(name).child(""+imageUri).putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                    while (!urlTask.isSuccessful());
                     Uri downloadUrl = urlTask.getResult();
                     FirebaseDatabase.getInstance().getReference().child("users").child(patient_name).child("messages").push().setValue(new ChatMessage(edittext_chatbox.getText().toString(), name,downloadUrl.toString()));

                }
            });
        }
    }

    private void displayChatMessage() {

        FirebaseDatabase.getInstance().getReference().child("users").child(patient_name).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ChatMessageList.clear();
                for (DataSnapshot snap :dataSnapshot.getChildren()) {
                    ChatMessage message=snap.getValue(ChatMessage.class);
                    ChatMessageList.add(message);
                }
                adapter = new ChatMessageAdapter(ChatActivity.this, ChatMessageList);
                adapter.notifyDataSetChanged();
                mMessageRecycler.setAdapter(adapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}