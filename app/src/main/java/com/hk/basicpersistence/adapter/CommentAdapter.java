package com.hk.basicpersistence.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.hk.basicpersistence.R;
import com.hk.basicpersistence.databinding.CommentItemBinding;
import com.hk.basicpersistence.db.entity.CommentEntity;
import com.hk.basicpersistence.ui.CommentClickCallback;

public class CommentAdapter extends ListAdapter<CommentEntity, CommentAdapter.CommentViewHolder> {

    @Nullable
    private final CommentClickCallback mCommentClickCallback;

    public CommentAdapter(@Nullable CommentClickCallback mCommentClickCallback) {
        super(new AsyncDifferConfig.Builder<>(new DiffUtil.ItemCallback<CommentEntity>() {
            @Override
            public boolean areItemsTheSame(@NonNull CommentEntity oldItem, @NonNull CommentEntity newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull CommentEntity oldItem, @NonNull CommentEntity newItem) {
                return oldItem.getId() == newItem.getId()
                        && oldItem.getPostedAt().equals(newItem.getPostedAt())
                        && oldItem.getProductId() == newItem.getProductId()
                        && TextUtils.equals(oldItem.getText(), newItem.getText())
                        ;
            }
        }).build());
        this.mCommentClickCallback = mCommentClickCallback;
    }


    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentItemBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.comment_item, parent, false);

        binding.setCallback(mCommentClickCallback);

        return new CommentViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        holder.binding.setComment(getItem(position));
        holder.binding.executePendingBindings();
    }

    static class CommentViewHolder extends RecyclerView.ViewHolder {

        final CommentItemBinding binding;

        CommentViewHolder(CommentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
