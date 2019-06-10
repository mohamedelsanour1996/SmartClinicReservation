package com.example.smartclinicreservation.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.backendless.Backendless;
import com.example.smartclinicreservation.Common;
import com.example.smartclinicreservation.R;
import com.example.smartclinicreservation.model.Patient;
import com.example.smartclinicreservation.patient.IRecyclerItemSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class BookedPatientAdapter extends RecyclerView.Adapter<BookedPatientAdapter.MyViewHolder> {
    Context context;
    List<Patient> timeSlotList;
    String formated ;
    List<CardView>cardViewList;
    int i=1;
    final String name = Backendless.UserService.CurrentUser().getProperty("name").toString();
    public BookedPatientAdapter(Context context) {
        this.context = context;
        this.timeSlotList = new ArrayList<>();
    }

    public BookedPatientAdapter(Context context, List<Patient> timeSlotList,String formated) {
        this.context = context;
        this.timeSlotList = timeSlotList;
        this.formated=formated;
        cardViewList=new ArrayList<>();
    }

    @NonNull
    @Override
    public BookedPatientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View inflate = LayoutInflater.from(context).inflate(R.layout.booked_time_slot_item, parent, false);
        return new BookedPatientAdapter.MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final BookedPatientAdapter.MyViewHolder holder, final int position) {
        if(timeSlotList.size()==0){


        }
        else {
            Patient slotvalue=timeSlotList.get(position);
                holder.txt_number.setText(Common.convertTimeSlotToString(Integer.parseInt(slotvalue.getPosition())));
                holder.txt_name.setText("Name : "+slotvalue.getName());
                holder.txt_phone.setText("Gender : "+slotvalue.getGender());
                holder.txt_age.setText("Age : "+slotvalue.getAge());



        }

        holder.setiRecyclerItemSelectedListener(new IRecyclerItemSelectedListener() {
            @Override
            public void onItemSlectedListener(View view, final int position) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return timeSlotList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_number, txt_name,txt_phone,txt_age;
        CardView card_time_slot_booked;
        IRecyclerItemSelectedListener iRecyclerItemSelectedListener;

        public void setiRecyclerItemSelectedListener(IRecyclerItemSelectedListener iRecyclerItemSelectedListener) {
            this.iRecyclerItemSelectedListener = iRecyclerItemSelectedListener;
        }

        public MyViewHolder(View itemView) {
            super(itemView);
            card_time_slot_booked = itemView.findViewById(R.id.card_time_slot_booked);
            txt_number = itemView.findViewById(R.id.number);
            txt_name = itemView.findViewById(R.id.Patient_name);
            txt_phone = itemView.findViewById(R.id.Patient_phone);
            txt_age = itemView.findViewById(R.id.Patient_age);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            iRecyclerItemSelectedListener.onItemSlectedListener(v,getAdapterPosition());
        }
    }
}
