package com.example.smartclinicreservation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.example.smartclinicreservation.Common;
import com.example.smartclinicreservation.patient.IRecyclerItemSelectedListener;
import com.example.smartclinicreservation.R;
import com.example.smartclinicreservation.model.Patient;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MyTimeSlotAdapter extends RecyclerView.Adapter<MyTimeSlotAdapter.MyViewHolder> {
    Context context;
    List<Patient> timeSlotList;
    List<Patient> timeSlotList_patient = new ArrayList<>();
    String formated;
    List<CardView> cardViewList;
    FirebaseDatabase df = FirebaseDatabase.getInstance();
    final String name = Backendless.UserService.CurrentUser().getProperty("name").toString();

    public MyTimeSlotAdapter(Context context) {
        this.context = context;
        this.timeSlotList = new ArrayList<>();
    }

    public MyTimeSlotAdapter(Context context, List<Patient> timeSlotList, String formated) {
        this.context = context;
        this.timeSlotList = timeSlotList;
        this.formated = formated;
        cardViewList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(context).inflate(R.layout.time_slot_item, parent, false);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final String s = new StringBuilder(Common.convertTimeSlotToString(position)).toString();
        holder.txt_time.setText(s);
        String birthday = Backendless.UserService.CurrentUser().getProperty("age").toString();
        String gender = Backendless.UserService.CurrentUser().getProperty("gender").toString();
        final Patient p = new Patient(String.valueOf(position), name, birthday, gender);
        if (timeSlotList.size() == 0) {
            holder.timecardview.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));
            holder.txt_description.setText("متاح");
            holder.txt_description.setTextColor(context.getResources().getColor(android.R.color.black));
            holder.txt_time.setTextColor(context.getResources().getColor(android.R.color.black));

        } else {

            for (Patient slotvalue : timeSlotList) {

                int slot = Integer.parseInt(slotvalue.getPosition());
                if (slot == position) {
                    if (slotvalue.getName().equals(name)) {

                        holder.timecardview.setTag(Common.DISABE_TAG);
                        holder.timecardview.setCardBackgroundColor(context.getResources().getColor(android.R.color.holo_green_light));
                        holder.txt_description.setText("محجوز");
                        holder.txt_description.setTextColor(context.getResources().getColor(android.R.color.white));
                        holder.txt_time.setTextColor(context.getResources().getColor(android.R.color.white));
                        timeSlotList_patient.add(slotvalue);
                    } else {
                        holder.timecardview.setEnabled(false);
                        holder.timecardview.setTag(Common.DISABE_TAG);
                        holder.timecardview.setCardBackgroundColor(context.getResources().getColor(android.R.color.darker_gray));
                        holder.txt_description.setText("محجوز");
                        holder.txt_description.setTextColor(context.getResources().getColor(android.R.color.white));
                        holder.txt_time.setTextColor(context.getResources().getColor(android.R.color.white));
                    }

                }
            }
        }
        if (!cardViewList.contains(holder.timecardview)) {
            cardViewList.add(holder.timecardview);
        }
        holder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSlectedListener(View view, final int position) {

                if (holder.timecardview.getTag() == Common.DISABE_TAG) {
                    df.getReference().child("Doctor").child(formated).child("" + position).removeValue();

                    holder.timecardview.setCardBackgroundColor(context.getResources().getColor(android.R.color.white));
                    holder.txt_description.setText("متاح");
                    holder.txt_description.setTextColor(context.getResources().getColor(android.R.color.black));
                    holder.txt_time.setTextColor(context.getResources().getColor(android.R.color.black));

                    holder.timecardview.setTag(null);


                } else {

                    if (timeSlotList_patient.size() != 0) {
                        Toast.makeText(context, "اختر ميعاد واحد فقط ", Toast.LENGTH_LONG).show();

                    } else {
                        df.getReference().child("Doctor").child(formated).child("" + position).setValue(p);
                        holder.timecardview.setTag(Common.DISABE_TAG);
                    }
                }


            }
        });

    }

    @Override
    public int getItemCount() {
        return 12;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_time, txt_description;
        CardView timecardview;
        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            timecardview = itemView.findViewById(R.id.card_time_slot);
            txt_time = itemView.findViewById(R.id.txt_time_slot);
            txt_description = itemView.findViewById(R.id.txt_time_slot_description);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            iRecyclerItemSelectedListener.onItemSlectedListener(v, getAdapterPosition());
        }
    }
}
