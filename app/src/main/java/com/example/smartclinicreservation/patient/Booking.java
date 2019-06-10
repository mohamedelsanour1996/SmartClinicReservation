package com.example.smartclinicreservation.patient;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.smartclinicreservation.R;
import com.example.smartclinicreservation.SpacesItemDecoration;
import com.example.smartclinicreservation.adapter.MyTimeSlotAdapter;
import com.example.smartclinicreservation.model.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class Booking extends AppCompatActivity implements ITimeSlotLoadListener {

    Calendar selected_date;
    RecyclerView recycler_time_slot;
    HorizontalCalendarView calendarView;
    private FirebaseDatabase df = FirebaseDatabase.getInstance();
    String formattedDate;
    ITimeSlotLoadListener iTimeSlotLoadListener;
    TextView free;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        free = findViewById(R.id.free);
        iTimeSlotLoadListener = this;
        recycler_time_slot = findViewById(R.id.recycler_time_slot);
        calendarView = findViewById(R.id.calenderView);
        final Date c = Calendar.getInstance().getTime();
        final SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        formattedDate = df.format(c);
        selected_date = Calendar.getInstance();
        selected_date.add(Calendar.DATE, 0);
        recycler_time_slot.setHasFixedSize(true);
        recycler_time_slot.addItemDecoration(new SpacesItemDecoration(8));
        recycler_time_slot.setLayoutManager(new GridLayoutManager(Booking.this, 3));
        Calendar start_date = Calendar.getInstance();
        start_date.add(Calendar.DATE, 0);
        Calendar end_date = Calendar.getInstance();
        end_date.add(Calendar.DATE, 7);
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar
                .Builder(Booking.this, R.id.calenderView)
                .range(start_date, end_date).datesNumberOnScreen(1)
                .mode(HorizontalCalendar.Mode.DAYS)
                .defaultSelectedDate(start_date).build();
        horizontalCalendar.refresh();
        SimpleDateFormat mm = new SimpleDateFormat("EEE", Locale.ENGLISH);
        String form = mm.format(c);
        if (form.equals("Fri") || form.equals("Thu")) {
            recycler_time_slot.setVisibility(View.GONE);
            free.setVisibility(View.VISIBLE);
        } else {
            free.setVisibility(View.GONE);
            recycler_time_slot.setVisibility(View.VISIBLE);
            loadAvilableTimeSlotDoctor(formattedDate);
        }
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                if (selected_date.getTimeInMillis() != date.getTimeInMillis()) {
                    selected_date = date;
                    formattedDate = df.format(date.getTime());
                    SimpleDateFormat dff = new SimpleDateFormat("EEE", Locale.ENGLISH);
                    String day = dff.format(date.getTime());
                    if (day.equals("Fri") || day.equals("Thu")) {

                        recycler_time_slot.setVisibility(View.GONE);
                        free.setVisibility(View.VISIBLE);
                    } else {
                        free.setVisibility(View.GONE);
                        recycler_time_slot.setVisibility(View.VISIBLE);
                        loadAvilableTimeSlotDoctor(formattedDate);
                    }

                }

            }
        });

    }


    private void loadAvilableTimeSlotDoctor(final String formatted) {


        df.getReference().child("Doctor").child(formatted).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Patient> information = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Patient message = snapshot.getValue(Patient.class);
                    information.add(message);

                }

                iTimeSlotLoadListener.onTimeSlotLoadSuccess(information, formatted);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    @Override
    public void onTimeSlotLoadSuccess(List<Patient> timeSlotList, String formattedDate) {
        recycler_time_slot.setAdapter(new MyTimeSlotAdapter(Booking.this, timeSlotList, formattedDate));
    }

    @Override
    public void onTimeSlotLoadEmpty() {

    }
}
