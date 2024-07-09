package com.app.roomzy.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.roomzy.BuildConfig;
import com.app.roomzy.CartActivity;
import com.app.roomzy.Controller.CartDBManager;
import com.app.roomzy.Controller.CurrencyFormatter;
import com.app.roomzy.Fragments.ProductDetailFragment;
import com.app.roomzy.Models.Room;
import com.app.roomzy.R;
import com.app.roomzy.ViewAllActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.Viewholder> {
    ArrayList<Room> products = new ArrayList<>();
    Context context;
    FragmentManager fragmentManager;
    CartDBManager cartDBManager;


    int click =1 ;
    public CartAdapter(ViewAllActivity viewAllProductsActivity, ArrayList<Room> products) {
        context = viewAllProductsActivity;
        this.products = products;
    }

    public CartAdapter(CartActivity cartActivity, ArrayList<Room> arrayList, CartDBManager cartDBManager, FragmentManager fragmentManager) {
        this.context = cartActivity;
        this.fragmentManager=fragmentManager;
        this.products=arrayList;
        this.cartDBManager=cartDBManager;
    }

    public CartAdapter(FragmentActivity activity, ArrayList<Room> arrayList) {
        this.context = activity;
        this.products = arrayList;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_layout,parent,false);


        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        Room room = products.get(position);

        holder.pName.setText(room.getName());
        holder.pPrice.setText(CurrencyFormatter.formatVietnameseCurrency(room.getPrice()));
        holder.pDesc.setText(room.getAddress());

        Picasso.get().load(room.getImageURL())
                .into(holder.pImage);

        holder.likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click ==0 ){
                    holder.likeBtn.setImageResource(R.drawable.heart_filled2);
                    ++click;
                    if(cartDBManager.addRoomToCart(room)){

                        Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    holder.likeBtn.setImageResource(R.drawable.heart2);
                    --click;
                    if(cartDBManager.removeRoomFromCart(room.getId())){

                        Toast.makeText(context, "Removed from cart", Toast.LENGTH_SHORT).show();
                    }



                }

            }
        });
        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductDetailFragment bottomSheet = new ProductDetailFragment(context, room);
                bottomSheet.show(fragmentManager, "ModalBottomSheet");
            }
        });
    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{

        private TextView pName,pDesc,pPrice;
        private ImageView pImage,likeBtn,shareBtn;
        public Viewholder(@NonNull  View itemView) {
            super(itemView);
            pName = (TextView) itemView.findViewById(R.id.pName);
            pPrice = (TextView) itemView.findViewById(R.id.pPrice);
            pDesc = (TextView) itemView.findViewById(R.id.pDesc);
            pImage = (ImageView) itemView.findViewById(R.id.pImage);
            likeBtn = (ImageView) itemView.findViewById(R.id.likeBtn);
            shareBtn = (ImageView) itemView.findViewById(R.id.shareBtn);
        }
    }
}

