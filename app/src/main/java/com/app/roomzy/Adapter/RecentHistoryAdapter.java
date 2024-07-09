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

import com.app.roomzy.CartActivity;
import com.app.roomzy.Controller.CartDBManager;
import com.app.roomzy.Controller.CurrencyFormatter;
import com.app.roomzy.Controller.DatabaseHelper;
import com.app.roomzy.Controller.HistoryDBManager;
import com.app.roomzy.Fragments.ProductDetailFragment;
import com.app.roomzy.HistoryActivity;
import com.app.roomzy.Models.Room;
import com.app.roomzy.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecentHistoryAdapter extends RecyclerView.Adapter<RecentHistoryAdapter.Viewholder> {

    private ArrayList<Room> recentHistory = new ArrayList<>();
    private final Context context;
    int click =0;
    boolean isHome;
    HistoryDBManager historyDBManager;
    CartDBManager cartDBManager;
    FragmentManager fragmentManager;
    public RecentHistoryAdapter(ArrayList<Room> recentHistory, CartDBManager cartDBManager, Context context, androidx.fragment.app.FragmentManager fragmentManager) {
        this.recentHistory = recentHistory;
        this.context = context;
        this.cartDBManager=cartDBManager;
        this.fragmentManager = fragmentManager;
        this.isHome = true;
    }

    public RecentHistoryAdapter(HistoryActivity historyActivity, ArrayList<Room> arrayList, HistoryDBManager historyDBManager, FragmentManager fragmentManager) {
        this.context = historyActivity;
        this.historyDBManager=historyDBManager;
        this.recentHistory=arrayList;
        this.fragmentManager=fragmentManager;
        this.isHome = false;
    }
    @NonNull
    @Override
    public RecentHistoryAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_history,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentHistoryAdapter.Viewholder holder, int position) {
        Room room = recentHistory.get(position);

        holder.pName.setText(room.getName());
        holder.pPrice.setText(CurrencyFormatter.formatVietnameseCurrency(room.getPrice()));
        holder.pDesc.setText(room.getAddress());

        Picasso.get().load(room.getImageURL())
                .into(holder.pImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailFragment bottomSheet = new ProductDetailFragment(context, room);
                bottomSheet.show(fragmentManager, "model");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (isHome)
        {
            int limit = 4;

            if(recentHistory.size() > limit){
                return limit;
            }
            else
            {
                return recentHistory.size();
            }
        }else
            return recentHistory.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{
        private TextView pName,pDesc,pPrice;
        private ImageView pImage;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            pName = (TextView) itemView.findViewById(R.id.pName);
            pPrice = (TextView) itemView.findViewById(R.id.pPrice);
            pDesc = (TextView) itemView.findViewById(R.id.pDesc);
            pImage = (ImageView) itemView.findViewById(R.id.pImage);
        }
    }
}
