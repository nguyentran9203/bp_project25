package com.example.bpproject;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MessagesActivity extends AppCompatActivity {

    private RecyclerView recyclerMessages;
    private MessageAdapter adapter;
    private List<MessagePreview> messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        recyclerMessages = findViewById(R.id.recyclerMessages);

        messageList = new ArrayList<>();
        messageList.add(new MessagePreview("ClientA", "Are you available this week?", true));
        messageList.add(new MessagePreview("ClientB", "Thanks for the update!", false));

        adapter = new MessageAdapter(messageList, message -> {
            Intent intent = new Intent(MessagesActivity.this, ChatActivity.class);
            intent.putExtra("clientName", message.getUsername());
            startActivity(intent);
        });

        recyclerMessages.setAdapter(adapter);
    }
}


