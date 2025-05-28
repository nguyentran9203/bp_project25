package com.example.bpproject;

public class ChatMessage {
    private String message;
    private boolean isSender;
    private String timestamp;

    public ChatMessage(String message, boolean isSender, String timestamp) {
        this.message = message;
        this.isSender = isSender;
        this.timestamp = timestamp;
    }

    public String getMessage() { return message; }
    public boolean isSender() { return isSender; }
    public String getTimestamp() { return timestamp; }
}
