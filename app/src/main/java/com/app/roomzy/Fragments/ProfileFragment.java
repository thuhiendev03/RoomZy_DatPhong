package com.app.roomzy.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.roomzy.BookingHistoryActivity;
import com.app.roomzy.Controller.HistoryUpdated;
import com.app.roomzy.HistoryActivity;
import com.app.roomzy.LoginActivity;
import com.app.roomzy.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment  {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    RelativeLayout logoutBtn;
    RelativeLayout recentHistoryBtn;
    RelativeLayout bookingHistoryBtn;
    FirebaseAuth mAuth;



    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        recentHistoryBtn = view.findViewById(R.id.myaccount);
        recentHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), HistoryActivity.class);
                startActivity(intent);
            }
        });

        bookingHistoryBtn = view.findViewById(R.id.appBookingHistory);
        bookingHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), BookingHistoryActivity.class);
                startActivity(intent);
            }
        });

        logoutBtn = view.findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOutUser();
            }
        });

        return view;
    }

    private void logOutUser() {
        // Sign out from Firebase
        mAuth.signOut();

        // Remove user info from SharedPreferences
        SharedPreferences sharedPref = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.remove("user_id");
        editor.apply();

        // Show a logout success message
        Toast.makeText(getActivity(), "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Navigate back to the LoginActivity
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Clear all activities above LoginActivity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK); // Clear all tasks
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Start a new task
        startActivity(intent);
        getActivity().finish();
    }

}
