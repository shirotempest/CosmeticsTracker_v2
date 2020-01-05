package com.gmail.sirotempest.listedit;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.gmail.sirotempest.R;
import com.gmail.sirotempest.data.db.entity.Product;
import com.gmail.sirotempest.utils.Util;
//import com.gmail.sirotempest.utils.Util.productAboutToExpire;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    private List<Product> mValues;
    private ListContract.OnItemClickListener mOnItemClickListener;
    private Context context;

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
        holder.quantityTextView.setText(String.valueOf(mValues.get(position).quantity));

        holder.brandTextView.setText(holder.mItem.brand);
        holder.priceTextView.setText(String.valueOf(holder.mItem.price));
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

        /*if(Util.productAboutToExpire(String.valueOf(holder.mItem.expiryDate), context)){
            holder.expiryDateTextView.setTextColor(ContextCompat.getColor(context, R.color.expireText));
        }*/

        Date currentDate = new java.util.Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

        Date expiryDate = null;


        long diff;

        try {
            diff = holder.mItem.expiryDate.getTime() - currentDate.getTime();
            int daysLeft =  (int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            if(daysLeft < 30) {
                holder.expiryDateTextView.setTextColor(Color.RED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        //compare if there are more than x days with food expiration date, where x is periodicity defined at settings (default is 3 days)

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
