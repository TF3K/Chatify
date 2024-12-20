package com.glsi.chatify;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page); // Ensure this layout contains messagesRecyclerView

        // Initialize RecyclerView
        RecyclerView recyclerView = findViewById(R.id.messagesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Dummy data for testing
        List<Message> messages = new ArrayList<>();
        messages.add(new Message("Hi there!", true));  // Incoming
        messages.add(new Message("Hello!", false));   // Outgoing

        // Set Adapter
        MessageAdapter adapter = new MessageAdapter(messages);
        recyclerView.setAdapter(adapter);
    }
}
