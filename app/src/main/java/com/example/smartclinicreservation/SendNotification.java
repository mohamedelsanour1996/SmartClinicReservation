package com.example.smartclinicreservation;

import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

public class SendNotification {
    public SendNotification(String message,String title,String notificationKey){
        try {
            JSONObject notificationContent = new JSONObject(
                    "{'contents':{'en':'" + message + "'},"+
                            "'include_player_ids':['" + notificationKey + "']," +
                            "'headings':{'en': '" + title + "'}}");
            OneSignal.postNotification(notificationContent, null);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
