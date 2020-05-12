package com.hk.basicpersistence.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.hk.basicpersistence.R;
import com.hk.basicpersistence.databinding.ProductItemBinding;
import com.hk.basicpersistence.model.Product;
import com.hk.basicpersistence.ui.ProductClickCallback;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    List<? extends Product> mProductLists;

    @Nullable
    private final ProductClickCallback mProductClickCallback;

    public ProductAdapter(@Nullable ProductClickCallback mProductClickCallback) {
        this.mProductClickCallback = mProductClickCallback;
        setHasStableIds(true);
    }

    public void setProductList(final List<? extends Product> productList) {
        if (mProductLists == null) {
            mProductLists = productList;
            notifyItemRangeInserted(0, productList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return mProductLists.size();
                }

                @Override
                public int getNewListSize() {
                    return productList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return mProductLists.get(oldItemPosition).getId()
                            == productList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Product newProduct = productList.get(newItemPosition);
                    Product oldProduct = mProductLists.get(oldItemPosition);

                    return newProduct.getId() == oldProduct.getId()
                            && TextUtils.equals(newProduct.getDescription(), oldProduct.getDescription())
                            && TextUtils.equals(newProduct.getName(), oldProduct.getName())
                            && newProduct.getPrice() == oldProduct.getPrice();
                }
            });

            mProductLists = productList;
            result.dispatchUpdatesTo(this);
        }

    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ProductItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.product_item, parent, false);
        binding.setCallback(mProductClickCallback);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.binding.setProduct(mProductLists.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mProductLists == null ? 0 : mProductLists.size();
    }

    @Override
    public long getItemId(int position) {
        return mProductLists.get(position).getId();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {

        final ProductItemBinding binding;

        public ProductViewHolder(ProductItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
