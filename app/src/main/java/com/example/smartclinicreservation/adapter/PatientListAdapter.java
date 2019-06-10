package com.example.smartclinicreservation.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartclinicreservation.ChatActivity;
import com.example.smartclinicreservation.R;

import java.util.List;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.ViewHolder> {
    Context context;
    List<String> patientList;

    public PatientListAdapter(Context context, List<String> chatMessages) {
        this.context = context;
        this.patientList = chatMessages;
    }

    @NonNull
    @Override
    public PatientListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View inflate = LayoutInflater.from(context).inflate(R.layout.patient_list_item, parent, false);
            return new PatientListAdapter.ViewHolder(inflate);

    }

    @Override
    public void onBindViewHolder(@NonNull PatientListAdapter.ViewHolder holder, int position) {
        if(patientList.size()!=0){
            final String message= patientList.get(position);
            holder.patient.setText(message);
           holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(context, ChatActivity.class);
                    i.putExtra("name",message);
                    context.startActivity(i);
                }
            });
        }
        else{
            Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        TextView patient;
        public ViewHolder(View itemView) {
            super(itemView);
            patient=itemView.findViewById(R.id.patientname);
            cardView=itemView.findViewById(R.id.card);
        }
    }
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

}
