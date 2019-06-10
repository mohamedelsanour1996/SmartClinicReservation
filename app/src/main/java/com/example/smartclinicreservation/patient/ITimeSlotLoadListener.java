package com.example.smartclinicreservation.patient;

import com.example.smartclinicreservation.model.Patient;

import java.util.List;

public interface ITimeSlotLoadListener {
    void onTimeSlotLoadSuccess(List<Patient>timeSlotList, String formated);
    void onTimeSlotLoadEmpty();

}
