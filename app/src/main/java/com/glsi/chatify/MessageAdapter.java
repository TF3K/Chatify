package com.glsi.chatify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private List<Message> messageList;

    public MessageAdapter(List<Message> messages) {
        this.messageList = messages;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message_bubble, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);

        if (message.isIncoming()) {
            holder.incomingMessage.setText(message.getContent());
            holder.incomingMessage.setVisibility(View.VISIBLE);
            holder.outgoingMessage.setVisibility(View.GONE);
        } else {
            holder.outgoingMessage.setText(message.getContent());
            holder.outgoingMessage.setVisibility(View.VISIBLE);
            holder.incomingMessage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView incomingMessage;
        TextView outgoingMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            incomingMessage = itemView.findViewById(R.id.incomingMessage);
            outgoingMessage = itemView.findViewById(R.id.outgoingMessage);
        }
    }
}

