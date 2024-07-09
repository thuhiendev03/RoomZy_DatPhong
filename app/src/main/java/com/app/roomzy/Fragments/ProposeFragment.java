package com.app.roomzy.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.roomzy.Adapter.AllCartAdapter;
import com.app.roomzy.Adapter.CartColumnAdapter;
import com.app.roomzy.Controller.HistoryUpdated;
import com.app.roomzy.Controller.RoomController;
import com.app.roomzy.Models.Room;
import com.app.roomzy.R;

import java.util.ArrayList;
import java.util.Collections;


public class ProposeFragment extends Fragment implements HistoryUpdated {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProposeFragment() {
        // Required empty public constructor
    }

    RecyclerView mRecentView;
    ArrayList<Room> trendingList = new ArrayList<>();
    CartColumnAdapter cartColumnAdapter;
    public static HistoryUpdated historyUpdated;

    RoomController roomController;
    public static ProposeFragment newInstance(String param1, String param2) {
        ProposeFragment fragment = new ProposeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_propose, container, false);
        mRecentView = view.findViewById(R.id.allProductsView);
        roomController = new RoomController();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getTrending();
                cartColumnAdapter = new CartColumnAdapter(trendingList, getContext(), getActivity().getSupportFragmentManager());
                mRecentView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                mRecentView.setAdapter(cartColumnAdapter);
            }
        },1000);
        return view;
    }
    private void getTrending(){
        roomController.getAllRooms(new RoomController.OnRoomDataLoadedListener() {
            @Override
            public void onRoomDataLoaded(ArrayList<Room> roomList) {
                trendingList.clear();
//                trendingList.addAll(roomList);
//                Log.e("PhamNguyen", trendingList.toString());
//                cartColumnAdapter.notifyDataSetChanged();
//                List<Room> shuffledRoomList = new ArrayList<>(roomList);
                Collections.shuffle(roomList);

                // Add approximately 6 random items to trendingList, ensure no duplicates
                int numberOfItemsToAdd = Math.min(8, roomList.size());
                for (int i = 0; i < numberOfItemsToAdd; i++) {
                    trendingList.add(roomList.get(i));
                }

                Log.e("PhamNguyen", trendingList.toString());
                cartColumnAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void getUpdateResult(boolean isUpdated) {

    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }
}