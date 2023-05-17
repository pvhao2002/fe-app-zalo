package com.author.toan.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.recyclerview.widget.RecyclerView;

import com.author.toan.R;
import com.author.toan.databinding.RowFriendRequestBinding;
import com.author.toan.databinding.RowFriendsBinding;
import com.author.toan.models.User;
import com.author.toan.response.RequestAddFriend;
import com.author.toan.viewmodels.ChatViewModel;
import com.bumptech.glide.Glide;

import java.util.List;

import javax.annotation.Nonnull;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.FriendRequestViewHolder> {
    private final List<RequestAddFriend> requestAddFriends;
    private OnItemClickListener onItemClickListener;

    public FriendRequestAdapter(List<RequestAddFriend> requestAddFriends) {
        this.requestAddFriends = requestAddFriends;
    }

    @NonNull
    @Override
    public FriendRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowFriendRequestBinding rowFriendRequestBinding =
                DataBindingUtil.inflate(
                        LayoutInflater.from(parent.getContext()),
                        R.layout.row_friend_request,
                        parent,
                        false);
        return new FriendRequestViewHolder(rowFriendRequestBinding, onItemClickListener);
    }


    @Override
    public void onBindViewHolder(@NonNull FriendRequestViewHolder holder, int position) {
        holder.setBinding(requestAddFriends.get(position), position);

    }

    @Override
    public int getItemCount() {
        return requestAddFriends.size();
    }

    public interface OnItemClickListener {
        void itemClick(RequestAddFriend requestAddFriend);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public static class FriendRequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ObservableField<String> name = new ObservableField<>();
        private final RowFriendRequestBinding rowFriendRequestBinding;
        private final OnItemClickListener onItemClickListener;
        private RequestAddFriend requestAddFriend;

        public FriendRequestViewHolder(@Nonnull RowFriendRequestBinding itemView, OnItemClickListener onItemClickListener) {
            super(itemView.getRoot());
            this.rowFriendRequestBinding = itemView;
            this.onItemClickListener = onItemClickListener;
            itemView.getRoot().setOnClickListener(this);
        }

        public void setBinding(RequestAddFriend requestAddFriend, int position) {
            this.requestAddFriend = requestAddFriend;
            if (rowFriendRequestBinding.getFriendRequestViewHolder() == null) {
                rowFriendRequestBinding.setFriendRequestViewHolder(this);
            }
            Glide.with(rowFriendRequestBinding.getRoot().getContext())
                    .load(requestAddFriend.getOwner().getAvatar().getUrl())
                    .circleCrop()
                    .into(rowFriendRequestBinding.ivAvatar);
            name.set(requestAddFriend.getOwner().getName());
        }

        public void accept(){
            ChatViewModel.getInstance().answerRequestAddFriend(requestAddFriend.getId(),"yes");
        }

        public void reject() {
            ChatViewModel.getInstance().answerRequestAddFriend(requestAddFriend.getId(),"no");
        }

        @Override
        public void onClick(View view) {
            this.onItemClickListener.itemClick(requestAddFriend);
        }
    }
}
