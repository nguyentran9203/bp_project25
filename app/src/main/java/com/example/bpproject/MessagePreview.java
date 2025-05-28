package com.example.bpproject;

public class MessagePreview {
    private String username;
    private String lastMessage;
    private boolean unread;

    public MessagePreview(String username, String lastMessage, boolean unread) {
        this.username = username;
        this.lastMessage = lastMessage;
        this.unread = unread;
    }

    public String getUsername() { return username; }
    public String getLastMessage() { return lastMessage; }
    public boolean isUnread() { return unread; }
}
