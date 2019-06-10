package com.example.smartclinicreservation.model;
import java.util.Date;

/**
 * Created by reale on 18/11/2016.
 */

public class ChatMessage {
    private String messageText;
    private String from;
    private long messageTime;
    private String  imageUri;


    public ChatMessage(String messageText, String from) {
        this.messageText = messageText;
        this.from = from;

        messageTime = new Date().getTime();
    }

    public ChatMessage() {
    }

    public ChatMessage(String messageText, String from, String imageUri) {
        this.messageText = messageText;
        this.from = from;
        this.imageUri = imageUri;
        messageTime = new Date().getTime();
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}