package com.example.bpproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public interface OnMessageClickListener {
        void onMessageClick(MessagePreview message);
    }

    private List<MessagePreview> messageList;
    private OnMessageClickListener listener;

    public MessageAdapter(List<MessagePreview> messageList, OnMessageClickListener listener) {
        this.messageList = messageList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        MessagePreview message = messageList.get(position);
        holder.usernameText.setText(message.getUsername());
        holder.previewText.setText(message.getLastMessage());
        holder.unreadDot.setVisibility(message.isUnread() ? View.VISIBLE : View.INVISIBLE);
        holder.itemView.setOnClickListener(v -> listener.onMessageClick(message));
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView usernameText, previewText;
        View unreadDot;

        ViewHolder(View view) {
            super(view);
            usernameText = view.findViewById(R.id.textUsername);
            previewText = view.findViewById(R.id.textLastMessage);
            unreadDot = view.findViewById(R.id.viewUnreadDot);
        }
    }
}
