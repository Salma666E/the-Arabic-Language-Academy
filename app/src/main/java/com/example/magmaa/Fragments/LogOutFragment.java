package com.example.magmaa.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.magmaa.pages.MainActivity;
import com.example.magmaa.R;
import com.example.magmaa.API_Mange.SessionMangment;


/**
 * A simple {@link Fragment} subclass.
 */
public class LogOutFragment extends Fragment {
    View view;
    Button mLogOutBtn, mCancelBtn;
    SessionMangment mSessionMangment;

    public LogOutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_log_out, container, false);
        mSessionMangment = new SessionMangment(getActivity());
        mLogOutBtn = view.findViewById(R.id.log_out);
        mLogOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSessionMangment.logoutUser();
                mSessionMangment.clearData();
            }
        });
        mCancelBtn = view.findViewById(R.id.cancel);
        mCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });
        return view;
    }}
