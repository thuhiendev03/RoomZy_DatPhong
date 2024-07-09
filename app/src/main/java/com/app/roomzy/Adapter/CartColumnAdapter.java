package com.app.roomzy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.roomzy.Controller.CurrencyFormatter;
import com.app.roomzy.Controller.HistoryUpdated;
import com.app.roomzy.Fragments.HomeFragment;
import com.app.roomzy.Fragments.ProductDetailFragment;
import com.app.roomzy.Models.Room;
import com.app.roomzy.R;
import com.app.roomzy.ViewAllActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartColumnAdapter extends RecyclerView.Adapter<CartColumnAdapter.Viewholder> {
    Context context;
    ArrayList<Room> rooms = new ArrayList<>();
    FragmentManager fragmentManager;
    public CartColumnAdapter(ViewAllActivity viewAllProductsActivity, ArrayList<Room> locals) {
        context = viewAllProductsActivity;
        this.rooms=locals;
    }
    public CartColumnAdapter(ArrayList<Room> rooms, Context context, FragmentManager supportFragmentManager) {
        context = context;
        this.rooms=rooms;
        this.fragmentManager=supportFragmentManager;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cart_column,parent,false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Room model = rooms.get(position);

        holder.productPrice.setText(CurrencyFormatter.formatVietnameseCurrency(model.getPrice()));
        holder.productName.setText(model.getName());
        holder.productDesc.setText(model.getAddress());
        Picasso.get().load(model.getImageURL())
                .into(holder.productImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailFragment bottomSheet = new ProductDetailFragment(context, model, HomeFragment.historyUpdated);
                bottomSheet.show(fragmentManager, "ModalBottomSheet");
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        TextView productName,productPrice,productDesc;
        ImageView productImage;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.productName);
            productDesc = (TextView) itemView.findViewById(R.id.productDesc2);
            productPrice = (TextView) itemView.findViewById(R.id.productPrice);
            productImage = (ImageView) itemView.findViewById(R.id.productImage);
        }
    }
}
