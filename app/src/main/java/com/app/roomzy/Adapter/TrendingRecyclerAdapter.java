package com.app.roomzy.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.roomzy.Controller.CurrencyFormatter;
import com.app.roomzy.Controller.HistoryUpdated;
import com.app.roomzy.Fragments.ProductDetailFragment;
import com.app.roomzy.Models.Room;
import com.app.roomzy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class TrendingRecyclerAdapter extends RecyclerView.Adapter<TrendingRecyclerAdapter.Viewholder> {
    Context mContext;
    ArrayList<Room> trendingList = new ArrayList<>();
    FragmentManager supportFragmentManager;
    HistoryUpdated historyUpdated;

    public TrendingRecyclerAdapter(ArrayList<Room> trendingList, Context context, FragmentManager supportFragmentManager, HistoryUpdated historyUpdated) {
        mContext = context;
        this.trendingList=trendingList;
        this.supportFragmentManager=supportFragmentManager;
        this.historyUpdated=historyUpdated;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_all_view_home,parent,false);


        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  TrendingRecyclerAdapter.Viewholder holder, int position) {

        Room trendingProducts = trendingList.get(position);
        holder.productName.setText(trendingProducts.getName());
        holder.productPrice.setText(CurrencyFormatter.formatVietnameseCurrency(trendingProducts.getPrice())); // Đổi từ int sang String ở đây
        Picasso.get().load(trendingProducts.getImageURL())
                .into(holder.productImage);

        if(position == 0){
            holder.crownImage.setVisibility(View.VISIBLE);
        } else {
            holder.crownImage.setVisibility(View.GONE);
        }

        // Mở bottom sheet khi sản phẩm được nhấp
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Thực hiện hành động khi nhấp vào một sản phẩm
                ProductDetailFragment bottomSheet = new ProductDetailFragment(mContext, trendingProducts,historyUpdated);
                bottomSheet.show(supportFragmentManager, "ModalBottomSheet");
            }
        });
    }

    @Override
    public int getItemCount() {
        return trendingList.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{
        ImageView productImage;
        CircleImageView crownImage;
        TextView productName,productPrice;
        public Viewholder(@NonNull  View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.productImage);
            crownImage = (CircleImageView) itemView.findViewById(R.id.crown);
            productName = (TextView) itemView.findViewById(R.id.productName);
            productPrice = (TextView) itemView.findViewById(R.id.productPrice);
        }
    }
}
