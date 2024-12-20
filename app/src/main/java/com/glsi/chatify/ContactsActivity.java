package com.glsi.chatify;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ContactsActivity extends AppCompatActivity {

    private RecyclerView contactsRecyclerView;
    private TextView noContactsMessage;
    private List<String> contacts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_page);

        // Initialize views
        contactsRecyclerView = findViewById(R.id.contacts_recycler_view);
        noContactsMessage = findViewById(R.id.no_contacts_message);

        // Simulate fetching contacts (replace with real data fetching logic)
        contacts = getContacts();

        // Check if there are any contacts
        if (contacts.isEmpty()) {
            noContactsMessage.setVisibility(View.VISIBLE);
            contactsRecyclerView.setVisibility(View.GONE);
        } else {
            noContactsMessage.setVisibility(View.GONE);
            contactsRecyclerView.setVisibility(View.VISIBLE);

            // Set up RecyclerView
            contactsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            ContactsAdapter adapter = new ContactsAdapter(contacts);
            contactsRecyclerView.setAdapter(adapter);
        }
    }

    // Simulate a method to fetch contacts
    private List<String> getContacts() {
        // Return an empty list for now; replace with actual data source
        return new ArrayList<>();
    }
}
