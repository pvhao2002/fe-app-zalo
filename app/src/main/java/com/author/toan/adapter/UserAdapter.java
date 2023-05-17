package com.author.toan.adapter;


import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.author.toan.R;
import com.author.toan.databinding.RowUserBinding;
import com.author.toan.models.User;
import com.author.toan.viewmodels.ChatViewModel;
import com.bumptech.glide.Glide;

import java.util.List;

import javax.annotation.Nonnull;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    private List<User> userList;
    private OnItemClickListener onItemClickListener;


    public UserAdapter(List<User> uList) {
        this.userList = uList;
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setListner(List<User> uList) {
        this.userList = uList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        RowUserBinding rowChatBinding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.row_user,
                        parent,
                        false);
        return new UserViewHolder(rowChatBinding, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.setBinding(userList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return userList == null ? 0 : userList.size();
    }

    public interface OnItemClickListener {
        void itemClick(User chat);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ObservableField<String> name = new ObservableField<>();
        public ObservableField<String> phonenumber = new ObservableField<>();
        private final RowUserBinding rowUserBinding;
        private OnItemClickListener onItemClickListener;
        private User user;

        public UserViewHolder(@Nonnull RowUserBinding itemView, OnItemClickListener onItemClickListener) {
            super(itemView.getRoot());
            this.rowUserBinding = itemView;
            this.onItemClickListener = onItemClickListener;
            itemView.getRoot().setOnClickListener(this);
        }

        public void setBinding(User user, int position) {
            this.user = user;
            if (rowUserBinding.getChatViewHodel() == null) {
                rowUserBinding.setChatViewHodel(this);
            }
            Log.e("UserAdapter", user.toString());
            Glide.with(rowUserBinding.getRoot().getContext())
                    .load(user.getAvatar().getUrl())
                    .circleCrop()
                    .into(rowUserBinding.imageViewAvatar);
            name.set(user.getName());
            phonenumber.set(user.getPhone());
        }

        public void addFriend() {
            ChatViewModel.getInstance().addFriend(user.getId());
        }

        @Override
        public void onClick(View view) {
            this.onItemClickListener.itemClick(user);
        }
    }
}
