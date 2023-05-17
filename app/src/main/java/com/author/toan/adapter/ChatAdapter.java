package com.author.toan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.author.toan.R;
import com.author.toan.databinding.RowChatBinding;
import com.author.toan.models.Chat;
import com.author.toan.remote.SharedPrefManager;
import com.bumptech.glide.Glide;

import java.util.List;

import javax.annotation.Nonnull;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private final List<Chat> chatList;
    private OnItemClickListener onItemClickListener;

    public ChatAdapter(List<Chat> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowChatBinding rowChatBinding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.row_chat,
                        parent,
                        false);
        return new ChatViewHolder(rowChatBinding, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.setBinding(chatList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public interface OnItemClickListener {
        void itemClick(Chat chat);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ObservableField<String> name = new ObservableField<>();
        public ObservableField<String> lastMessage = new ObservableField<>();
        private final RowChatBinding rowChatBinding;
        private final OnItemClickListener onItemClickListener;
        private Chat chat;

        public ChatViewHolder(@Nonnull RowChatBinding itemView, OnItemClickListener onItemClickListener) {
            super(itemView.getRoot());
            this.rowChatBinding = itemView;
            this.onItemClickListener = onItemClickListener;
            itemView.getRoot().setOnClickListener(this);
        }

        public void setBinding(Chat chat, int position) {
            this.chat = chat;
            if (rowChatBinding.getChatViewHodel() == null) {
                rowChatBinding.setChatViewHodel(this);
            }
            if (chat.getUsers().get(0).getId().equals(SharedPrefManager.getUser().getId())) {
                Glide.with(rowChatBinding.getRoot().getContext())
                        .load(chat.getUsers().get(1).getAvatar().getUrl())
                        .circleCrop()
                        .into(rowChatBinding.ivAvatar);
                name.set(chat.getName());
            }
            else {
                Glide.with(rowChatBinding.getRoot().getContext())
                        .load(chat.getUsers().get(0).getAvatar().getUrl())
                        .circleCrop()
                        .into(rowChatBinding.ivAvatar);
                name.set(chat.getName());
            }
            if (chat.getMessages().size() > 0) {
                lastMessage.set(chat.getMessages().get(chat.getMessages().size() - 1).getContent());
            }
        }

        @Override
        public void onClick(View view) {
            this.onItemClickListener.itemClick(chat);
        }
    }
}
