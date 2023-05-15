package com.author.toan.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.author.toan.R;
import com.author.toan.databinding.RowMessageReceiverBinding;
import com.author.toan.databinding.RowMessageSenderBinding;
import com.author.toan.models.Message;

import java.util.List;

import javax.annotation.Nonnull;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_SENDER = 0;
    private final int VIEW_TYPE_RECEIVER = 1;
    private final List<Message> messageList;

    public MessageAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_SENDER) {
            com.author.toan.databinding.RowMessageSenderBinding rowMessageSenderBinding =
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.getContext()),
                            R.layout.row_message_sender,
                            parent,
                            false);
            return new MessageAdapter.SenderViewHolder(rowMessageSenderBinding);
        }
        else if (viewType == VIEW_TYPE_RECEIVER) {
            RowMessageReceiverBinding rowMessageReceiverBinding =
                    DataBindingUtil.inflate(
                            LayoutInflater.from(parent.getContext()),
                            R.layout.row_message_receiver,
                            parent,
                            false);
            return new MessageAdapter.ReceiverViewHolder(rowMessageReceiverBinding);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SenderViewHolder) {
            ((SenderViewHolder) holder).setBinding(messageList.get(position), position);

        } else if (holder instanceof ReceiverViewHolder) {
            ((ReceiverViewHolder) holder).setBinding(messageList.get(position), position);
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public static class SenderViewHolder extends RecyclerView.ViewHolder {
        public ObservableField<String> Smessage = new ObservableField<>();
        private final RowMessageSenderBinding rowMessageSenderBinding;

        public SenderViewHolder(@Nonnull RowMessageSenderBinding itemView) {
            super(itemView.getRoot());
            this.rowMessageSenderBinding = itemView;
        }

        public void setBinding(Message message, int position) {
            if (rowMessageSenderBinding.getMessageViewHolder() == null) {
                rowMessageSenderBinding.setMessageViewHolder(this);
            }
            Smessage.set(message.getContent());
        }
    }

    public static class ReceiverViewHolder extends RecyclerView.ViewHolder {
        public ObservableField<String> Smessage = new ObservableField<>();
        private final RowMessageReceiverBinding rowMessageReceiverBinding;

        public ReceiverViewHolder(@Nonnull RowMessageReceiverBinding itemView) {
            super(itemView.getRoot());
            this.rowMessageReceiverBinding = itemView;
        }

        public void setBinding(Message message, int position) {
            if (rowMessageReceiverBinding.getMessageViewHolder() == null) {
                rowMessageReceiverBinding.setMessageViewHolder(this);
            }
            Smessage.set(message.getContent());
        }
    }

}
