package com.example.bpproject;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;

public class ChatActivity extends AppCompatActivity {

    private RecyclerView recyclerChat;
    private EditText inputMessage;
    private ImageButton buttonSend;

    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerChat = findViewById(R.id.recyclerChat);
        recyclerChat.setLayoutManager(new LinearLayoutManager(this)); // ✅ Required
        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages);
        recyclerChat.setAdapter(chatAdapter);

        // Get the passed username
        String clientName = getIntent().getStringExtra("clientName");

        // Set header name
        TextView headerUsername = findViewById(R.id.textUsername);
        headerUsername.setText(clientName);


        inputMessage = findViewById(R.id.editMessage);
        buttonSend = findViewById(R.id.buttonSend);

        chatMessages = new ArrayList<>();
        chatAdapter = new ChatAdapter(chatMessages);
        recyclerChat.setAdapter(chatAdapter);

        // Simulate messages
        chatMessages.add(new ChatMessage("Hey there! Thanks for reaching out.", false, "23 May 2025 11:59 PM"));
        chatMessages.add(new ChatMessage("You're welcome! I'm free this weekend.", true, "23 May 2025 12:00 AM"));
        chatAdapter.notifyDataSetChanged();

        buttonSend.setOnClickListener(v -> {
            String message = inputMessage.getText().toString().trim();
            if (!message.isEmpty()) {
                chatMessages.add(new ChatMessage(message, true, "Just now"));
                chatAdapter.notifyItemInserted(chatMessages.size() - 1);
                recyclerChat.scrollToPosition(chatMessages.size() - 1);
                inputMessage.setText("");
            }
        });
    }
}