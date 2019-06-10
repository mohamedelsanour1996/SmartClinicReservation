package com.example.smartclinicreservation;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

public class Common {
    public static final Object DISABE_TAG ="Disable" ;

    public static String convertTimeSlotToString(int slot) {
        switch (slot) {
            case 0:
                return "6:00 - 6:15";
            case 1:
                return "6:15 - 6:30";
            case 2:
                return "6:30 - 6:45";
            case 3:
                return "6:45 - 7:00";
            case 4:
                return "7:00 - 7:15";
            case 5:
                return "7:15 - 7:30";
            case 6:
                return "7:30 - 7:45";
            case 7:
                return "7:45 - 8:00";
            case 8:
                return "8:00 - 8:15";
            case 9:
                return "8:15 - 8:30";
            case 10:
                return "8:30 - 8:45";
            case 11:
                return "8:45 - 9:00";

            default:
                return "Closed";
        }

    }
}
