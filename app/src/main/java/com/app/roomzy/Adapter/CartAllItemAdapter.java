package com.app.roomzy.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.roomzy.BuildConfig;
import com.app.roomzy.Controller.CurrencyFormatter;
import com.app.roomzy.Controller.HistoryUpdated;
import com.app.roomzy.Fragments.HomeFragment;
import com.app.roomzy.Fragments.ProductDetailFragment;
import com.app.roomzy.Models.Room;
import com.app.roomzy.R;
import com.app.roomzy.ViewAllActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAllItemAdapter extends RecyclerView.Adapter<CartAllItemAdapter.ViewHolder> {

    ArrayList<Room> products = new ArrayList<>();
    Context context;
    Context mContext;
    FragmentManager fragmentManager;
    HistoryUpdated historyUpdated;
    public CartAllItemAdapter(ViewAllActivity viewAllProductsActivity, ArrayList<Room> products,  HistoryUpdated historyUpdated) {
        context = viewAllProductsActivity;
        this.products = products;
        this.historyUpdated= historyUpdated;

    }
    public CartAllItemAdapter(ViewAllActivity viewAllProductsActivity, ArrayList<Room> products) {
        context = viewAllProductsActivity;
        this.products = products;

    }

    public CartAllItemAdapter(FragmentActivity activity, ArrayList<Room> arrayList, HistoryUpdated historyUpdated) {
        this.context = activity;
        this.products = arrayList;
        this.historyUpdated= historyUpdated;

    }

    public CartAllItemAdapter(FragmentActivity activity, ArrayList<Room> arrayList , FragmentManager fragmentManager, HistoryUpdated historyUpdated) {
        this.context = activity;
        this.products = arrayList;
        this.fragmentManager = fragmentManager;
        this.historyUpdated= historyUpdated;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_all_item,parent,false);


        return new CartAllItemAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Room model = products.get(position);
        holder.productName.setText(model.getName());
        holder.productPrice.setText(CurrencyFormatter.formatVietnameseCurrency(model.getPrice()));
        holder.productDescription.setText(model.getAddress());
        Picasso.get().load(model.getImageURL())
                .into(holder.productImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager manager = ((AppCompatActivity)context).getSupportFragmentManager();
                ProductDetailFragment bottomSheet = new ProductDetailFragment(context, model, HomeFragment.historyUpdated);
                bottomSheet.show(manager, "ModalBottomSheet");
            }
        });
        holder.productShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Download the RoomZy App from the google play store in order to view the product : https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                context.startActivity(sendIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends  RecyclerView.ViewHolder {
        TextView productName,productPrice,productDescription;
        ImageView productImage,productShare;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.pName);
            productDescription = (TextView) itemView.findViewById(R.id.pDesc);
            productPrice = (TextView) itemView.findViewById(R.id.pPrice);
            productImage = (ImageView) itemView.findViewById(R.id.pImage);
            productShare = (ImageView) itemView.findViewById(R.id.shareBtn);
        }
    }
}
