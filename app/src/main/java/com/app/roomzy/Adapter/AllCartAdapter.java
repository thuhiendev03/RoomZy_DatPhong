package com.app.roomzy.Adapter;

import android.content.Context;
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

import java.text.BreakIterator;
import java.util.ArrayList;

public class AllCartAdapter extends RecyclerView.Adapter<AllCartAdapter.Viewholder> {
    Context mContext;
    ArrayList<Room> trendingList = new ArrayList<>();
    FragmentManager supportFragmentManager;
    HistoryUpdated historyUpdated;

    public AllCartAdapter(ArrayList<Room> trendingList, Context context, FragmentManager supportFragmentManager, HistoryUpdated historyUpdated) {
        mContext = context;
        this.trendingList=trendingList;
        this.supportFragmentManager=supportFragmentManager;
        this.historyUpdated=historyUpdated;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_all_cart,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  AllCartAdapter.Viewholder holder, int position) {

        Room trendingProducts = trendingList.get(position);
        holder.productName.setText(trendingProducts.getName());
        holder.productPrice.setText(CurrencyFormatter.formatVietnameseCurrency(trendingProducts.getPrice()));
        Picasso.get().load(trendingProducts.getImageURL())
                .into(holder.productImage);

        //opening bottom sheet
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailFragment bottomSheet = new ProductDetailFragment(mContext, trendingProducts,historyUpdated);
                bottomSheet.show(supportFragmentManager, "ModalBottomSheet");
            }
        });
    }

    @Override
    public int getItemCount() {
        int limit = 9;
        if(trendingList.size() > limit){
            return limit;
        }
        else
        {
            return trendingList.size();
        }

    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        public BreakIterator productDescription;
        ImageView productImage;
        TextView productName,productPrice;
        public Viewholder(@NonNull  View itemView) {
            super(itemView);
            productImage = (ImageView) itemView.findViewById(R.id.productImage);
            productName = (TextView) itemView.findViewById(R.id.productName);
            productPrice = (TextView) itemView.findViewById(R.id.productPrice);
        }
    }
}
