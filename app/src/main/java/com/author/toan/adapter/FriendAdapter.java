package com.author.toan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.author.toan.R;
import com.author.toan.databinding.RowFriendsBinding;
import com.author.toan.models.User;
import com.bumptech.glide.Glide;

import java.util.List;

import javax.annotation.Nonnull;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.FriendViewHolder> {
    private final List<User> userList;
    private FriendAdapter.OnItemClickListener onItemClickListener;

    public FriendAdapter(List<User> userList) {
        this.userList = userList;
    }

    @NonNull
    @Override
    public FriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowFriendsBinding rowFriendsBinding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.row_friends,
                        parent,
                        false);
        return new FriendViewHolder(rowFriendsBinding, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendViewHolder holder, int position) {
        holder.setBinding(userList.get(position), position);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public interface OnItemClickListener {
        void itemClick(User user);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class FriendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ObservableField<String> name = new ObservableField<>();
        private final RowFriendsBinding rowFriendsBinding;
        private final OnItemClickListener onItemClickListener;
        private User user;

        public FriendViewHolder(@Nonnull RowFriendsBinding itemView, OnItemClickListener onItemClickListener) {
            super(itemView.getRoot());
            this.rowFriendsBinding = itemView;
            this.onItemClickListener = onItemClickListener;
            itemView.getRoot().setOnClickListener(this);
        }

        public void setBinding(User user, int position) {
            this.user = user;
            if (rowFriendsBinding.getFriendViewHolder() == null) {
                rowFriendsBinding.setFriendViewHolder(this);
            }
            Glide.with(rowFriendsBinding.getRoot().getContext())
                    .load(user.getAvatar().getUrl())
                    .circleCrop()
                    .into(rowFriendsBinding.ivAvatar);
            name.set(user.getName());
        }

        @Override
        public void onClick(View view) {
            this.onItemClickListener.itemClick(user);
        }
    }
}
