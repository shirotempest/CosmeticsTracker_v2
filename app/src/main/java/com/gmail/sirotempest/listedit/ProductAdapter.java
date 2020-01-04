package com.gmail.sirotempest.listedit;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.gmail.sirotempest.R;
import com.gmail.sirotempest.data.db.entity.Product;
import com.gmail.sirotempest.utils.Util;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> mValues;
    private ListContract.OnItemClickListener mOnItemClickListener;

    public ProductAdapter(ListContract.OnItemClickListener onItemClickListener) {
        mValues = new ArrayList<>();
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.nameTextView.setText(mValues.get(position).name);
        holder.quantityTextView.setText(mValues.get(position).quantity);

        holder.brandTextView.setText(holder.mItem.brand);
        holder.priceTextView.setText(holder.mItem.price);
        holder.expiryDateTextView.setText(Util.formatMin(holder.mItem.expiryDate));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.clickItem(holder.mItem);
            }
        });

        holder.mView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemClickListener.clickLongItem(holder.mItem);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void setValues(List<Product> values) {
        mValues = values;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView nameTextView;
        public final TextView quantityTextView;
        public final TextView priceTextView;
        public final TextView expiryDateTextView;
        public final TextView brandTextView;
        public Product mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nameTextView = (TextView) view.findViewById(R.id.nameTextView);
            quantityTextView = (TextView) view.findViewById(R.id.quantityTextView);
            priceTextView = (TextView) view.findViewById(R.id.priceTextView);
            expiryDateTextView = (TextView) view.findViewById(R.id.expiryDateTextView);
            brandTextView = (TextView) view.findViewById(R.id.brandTextView);
        }
    }


}
