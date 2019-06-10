package com.example.smartclinicreservation.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.backendless.Backendless;
import com.bumptech.glide.Glide;
import com.example.smartclinicreservation.R;
import com.example.smartclinicreservation.model.ChatMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageAdapter extends RecyclerView.Adapter {
    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;
    String name = Backendless.UserService.CurrentUser().getProperty("name").toString().trim();
    private Context mContext;
    private List<ChatMessage> mMessageList;
    public ChatMessageAdapter(Context context) {
        mContext = context;
        mMessageList = new ArrayList<>();
    }

    public ChatMessageAdapter(Context context, List<ChatMessage> messageList) {
        mContext = context;
        mMessageList = messageList;
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }

    // Determines the appropriate ViewType according to the sender of the message.
    @Override
    public int getItemViewType(int position) {
        ChatMessage message = mMessageList.get(position);

        if (message.getFrom().equals(name)) {
            // If the current user is the sender of the message
            return VIEW_TYPE_MESSAGE_SENT;
        } else {
            // If some other user sent the message
            return VIEW_TYPE_MESSAGE_RECEIVED;
        }
    }

    // Inflates the appropriate layout according to the ViewType.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_message_sent, parent, false);
            return new SentMessageHolder(view);
        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(mContext)
                    .inflate(R.layout.item_message_received, parent, false);
            return new ReceivedMessageHolder(view);
        }

        return null;
    }

    // Passes the message object to a ViewHolder so that the contents can be bound to UI.
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ChatMessage message = mMessageList.get(position);

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_MESSAGE_SENT:
                ((SentMessageHolder) holder).bind(message);
                break;
            case VIEW_TYPE_MESSAGE_RECEIVED:
                ((ReceivedMessageHolder) holder).bind(message);
        }
    }

    private class SentMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText;
        ImageView messageImage;

        SentMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.text_message_body);
            timeText = itemView.findViewById(R.id.text_message_time);
            messageImage=itemView.findViewById(R.id.message_image);

        }

        void bind(ChatMessage message) {

            if (message.getImageUri().isEmpty())
            {
                messageText.setText(""+message.getMessageText());

                // Format the stored timestamp into a readable String using method.
                timeText.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", message.getMessageTime()));
                messageImage.setVisibility(View.GONE);
            }
            else {
                messageText.setText("image"+message.getMessageText());
                Glide.with(mContext).load(message.getImageUri()).into(messageImage);
            }

        }
    }

    private class ReceivedMessageHolder extends RecyclerView.ViewHolder {
        TextView messageText, timeText, nameText;
        ImageView messageImage;

        ReceivedMessageHolder(View itemView) {
            super(itemView);

            messageText = itemView.findViewById(R.id.text_message_body);
            timeText = itemView.findViewById(R.id.text_message_time);
            nameText = itemView.findViewById(R.id.text_message_name);
            messageImage=itemView.findViewById(R.id.message_image);
        }

        void bind(ChatMessage message) {


            // Format the stored timestamp into a readable String using method.
            nameText.setText(message.getFrom());
            if (message.getImageUri().isEmpty())
            {

                messageText.setText(""+message.getMessageText());
                timeText.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", message.getMessageTime()));
                messageImage.setVisibility(View.GONE);

            }
            else {
                messageText.setText("image"+message.getMessageText());
                Glide.with(mContext).load(message.getImageUri()).into(messageImage);
            }



        }
    }
}