package com.app.roomzy.Fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.roomzy.Adapter.AllCartAdapter;
import com.app.roomzy.Adapter.BannerAdapter;
import com.app.roomzy.Adapter.CategoriesAdapter;
import com.app.roomzy.Adapter.RecentHistoryAdapter;
import com.app.roomzy.Adapter.TrendingRecyclerAdapter;
import com.app.roomzy.Controller.CartDBManager;
import com.app.roomzy.Controller.DatabaseHelper;
import com.app.roomzy.Controller.HistoryDBManager;
import com.app.roomzy.Controller.HistoryUpdated;
import com.app.roomzy.Controller.LocationController;
import com.app.roomzy.Controller.RoomController;
import com.app.roomzy.Models.CategoriesModel;
import com.app.roomzy.Models.LocationModel;
import com.app.roomzy.Models.Room;
import com.app.roomzy.R;
import com.app.roomzy.ViewAllActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements HistoryUpdated {

    LinearLayout noHistoryImage;
    View mMainView;
    Button viewAllBtn,viewlocalAllBtn;
    RecyclerView mTrendingView,mProductsAcrossView,mCategoriesView,mLocalViews,mRecentView;
    ImageView bannerImage,midbannerImage;
    NestedScrollView nestedScrollView;
    CategoriesAdapter mAdapter3;

    HistoryDBManager productsHistory;
    RecentHistoryAdapter mRecentAdapter;

    LocationController locationController;


    ArrayList<Room> trendingList = new ArrayList<>();
    ArrayList<CategoriesModel>categoriesList = new ArrayList<>();
    ArrayList<LocationModel>locationArrayList = new ArrayList<>();
    ArrayList<Room>topVNList = new ArrayList<>();
    ArrayList<Room>toplocationList = new ArrayList<>();

    ArrayList<Room>recentHistory = new ArrayList<>();

    ArrayList<String>ranks = new ArrayList<>();

    Button clearHistory;
    private RoomController roomController;
    public static HistoryUpdated historyUpdated;
    TrendingRecyclerAdapter trendingRecyclerAdapter;
    AllCartAdapter topVNRecyclerAdapter;
    AllCartAdapter topLocationAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void initialize(){
        mTrendingView = (RecyclerView) mMainView.findViewById(R.id.trendingView);
        mProductsAcrossView = (RecyclerView) mMainView.findViewById(R.id.productsAcrossIndia);
        mCategoriesView = (RecyclerView) mMainView.findViewById(R.id.categoriesView);
        mRecentView = (RecyclerView) mMainView.findViewById(R.id.recentProduct);
        mLocalViews = (RecyclerView) mMainView.findViewById(R.id.suportLocals);
//        bannerImage = (ImageView) mMainView.findViewById(R.id.bannerHome);
        midbannerImage = (ImageView) mMainView.findViewById(R.id.midBanner);
        clearHistory = (Button) mMainView.findViewById(R.id.clearHistory);
        noHistoryImage = (LinearLayout) mMainView.findViewById(R.id.noHistory);
        viewAllBtn = (Button) mMainView.findViewById(R.id.viewAllBtn);
        viewlocalAllBtn = (Button) mMainView.findViewById(R.id.viewlocalsBtn);
        nestedScrollView = (NestedScrollView) mMainView.findViewById(R.id.nestedScroll);
        historyUpdated = (HistoryUpdated) this;


        ViewPager2 bannerViewPager = mMainView.findViewById(R.id.bannerViewPager);

        ArrayList<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.promotion50);
        imageList.add(R.drawable.promotion);
        imageList.add(R.drawable.promotion30);

        BannerAdapter bannerAdapter = new BannerAdapter(imageList);
        bannerViewPager.setAdapter(bannerAdapter);

        // Optional: Set auto-slide
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            int position = 0;

            @Override
            public void run() {
                if (position == imageList.size())
                    position = 0;
                bannerViewPager.setCurrentItem(position++, true);
                handler.postDelayed(this, 3000); // Slide interval 3 seconds
            }
        };
        handler.postDelayed(runnable, 3000);

        midbannerImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:phamnguyenkali@gmail.com")); // only email apps should handle this
                intent.putExtra(Intent.EXTRA_SUBJECT, "here is my valueable suggestion for you.");
                if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

//        bannerImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_SENDTO);
//                intent.setData(Uri.parse("mailto:phamnguyenkali@gmail.com")); // only email apps should handle this
//                intent.putExtra(Intent.EXTRA_SUBJECT, "here is my valueable suggestion for you.");
//                if (intent.resolveActivity(requireContext().getPackageManager()) != null) {
//                    startActivity(intent);
//                }
//            }
//        });
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mMainView = inflater.inflate(R.layout.fragment_home, container, false);
        initialize();
        roomController = new RoomController();
        locationController = new LocationController();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
//                addCategories();
                getLoCationList();
                getTopVnList();
                getTrending();
                getLocations();

                Log.e("PhamNguyen", trendingList.toString());
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
                trendingRecyclerAdapter = new TrendingRecyclerAdapter(trendingList,getContext(),requireActivity().getSupportFragmentManager(),historyUpdated);
                mTrendingView.setLayoutManager(linearLayoutManager);
                mTrendingView.setAdapter(trendingRecyclerAdapter);


                //categories adapter
                LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
                mCategoriesView.setLayoutManager(linearLayoutManager2);
                mAdapter3 = new CategoriesAdapter(locationArrayList,getContext());
                mCategoriesView.setAdapter(mAdapter3);

                mProductsAcrossView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                topVNRecyclerAdapter = new AllCartAdapter(topVNList,getContext(),requireActivity().getSupportFragmentManager(),historyUpdated);
                mProductsAcrossView.setAdapter(topVNRecyclerAdapter);


                topLocationAdapter = new AllCartAdapter(toplocationList,getContext(),requireActivity().getSupportFragmentManager(),historyUpdated);
                mLocalViews.setLayoutManager(new GridLayoutManager(getContext(), 3));
                mLocalViews.setAdapter(topLocationAdapter);


                productsHistory = new HistoryDBManager(getContext());
                recentHistory = productsHistory.getAllViewedRooms();
                CartDBManager cartDBManager = new CartDBManager(getContext());

                mRecentAdapter = new RecentHistoryAdapter(recentHistory,cartDBManager,getContext(),getFragmentManager());
                LinearLayoutManager linearLayoutManager4 = new LinearLayoutManager(getContext());
                mRecentView.setLayoutManager(linearLayoutManager4);
                mRecentView.setAdapter(mRecentAdapter);

            }
        },1000);
        if (recentHistory.size() == 0){
            noHistoryImage.setVisibility(View.VISIBLE);
        }else{
            noHistoryImage.setVisibility(View.GONE);
        }
        clearHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productsHistory.deleteRow();
                recentHistory.clear();
                noHistoryImage.setVisibility(View.VISIBLE);
                mRecentAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Product view history cleared", Toast.LENGTH_SHORT).show();
            }
        });

        viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewAllActivity.class);
                intent.putExtra("type","all");
                startActivity(intent);
            }
        });
        viewlocalAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ViewAllActivity.class);
//                intent.putParcelableArrayListExtra("list",);
//                intent.putExtra("type","local");
                intent.putExtra("type","all");
                startActivity(intent);
            }
        });


        return mMainView;
    }

    private void getTrending(){
        roomController.getAllRooms(new RoomController.OnRoomDataLoadedListener() {
            @Override
            public void onRoomDataLoaded(ArrayList<Room> roomList) {
                trendingList.clear();
                trendingList.addAll(roomList);
                Log.e("PhamNguyen", trendingList.toString());
                trendingRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }
    private void getLoCationList(){
        roomController.getAllRooms(new RoomController.OnRoomDataLoadedListener() {
            @Override
            public void onRoomDataLoaded(ArrayList<Room> roomList) {
                toplocationList.clear();
                toplocationList.addAll(roomList);
                Log.e("PhamNguyen", toplocationList.toString());
                topLocationAdapter.notifyDataSetChanged();
            }
        });
    }

    private void getTopVnList(){
        roomController.getAllRooms(new RoomController.OnRoomDataLoadedListener() {
            @Override
            public void onRoomDataLoaded(ArrayList<Room> roomList) {
                topVNList.clear();
                topVNList.addAll(roomList);
                Log.e("PhamNguyen", topVNList.toString());
                topVNRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }
    private void addCategories(){
        categoriesList.clear();

        categoriesList.add( new CategoriesModel("https://statics.vinpearl.com/is-ho-chi-minh-city-safe-01_1689422733.jpg","Hồ Chí Minh"));
        categoriesList.add( new CategoriesModel("https://vcdn1-dulich.vnecdn.net/2022/05/11/hoan-kiem-lake-7673-1613972680-1508-1652253984.jpg?w=0&h=0&q=100&dpr=1&fit=crop&s=2wB1cBTUcNKuk68nrG6LMQ","Hà Nội"));
        categoriesList.add( new CategoriesModel("https://bcp.cdnchinhphu.vn/344443456812359680/2022/12/27/nhattrang3-16721128389061596602579.jpg","Nha Trang"));
        categoriesList.add( new CategoriesModel("https://vcdn1-dulich.vnecdn.net/2022/06/01/CauVangDaNang-1654082224-7229-1654082320.jpg?w=0&h=0&q=100&dpr=2&fit=crop&s=MeVMb72UZA27ivcyB3s7Kg","Đà Nẵng"));
    }

    private  void getLocations()
    {
        locationController.getAllLocations(new LocationController.OnLocationDataLoadedListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onLocationDataLoaded(ArrayList<LocationModel> locationList) {
                locationArrayList.clear();
                locationArrayList.addAll(locationList);
                Log.e("PhamNguyen",locationArrayList.toString());
                mAdapter3.notifyDataSetChanged();
            }
        });
    }
    @Override
    public void getUpdateResult(boolean isUpdated) {
        if (isUpdated){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recentHistory.clear();
                    recentHistory.addAll(productsHistory.getAllViewedRooms());
                    mRecentAdapter.notifyDataSetChanged();
                    if(recentHistory.size() > 0)
                        noHistoryImage.setVisibility(View.GONE);
                }
            },1000);
        }
    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}