package com.example.magmaa.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.magmaa.R;
import com.example.magmaa.pages.Commisson.ComissionActivity;
import com.example.magmaa.pages.Meeting.MeetingActivity;
import com.example.magmaa.pages.PDFActivity;
import com.example.magmaa.pages.GeneralMeeting.GeneralMeetingActivity;

public class MangMogamaFragment extends Fragment implements View.OnClickListener {
    View v;
    TextView mRead ,mMeeting  ,mCommission , mGeneralMeeting , mLibrary;

    public MangMogamaFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v= inflater.inflate(R.layout.fragment_mang_mogama, container, false);
        initial();
        return v;
    }
    private void initial() {
        mRead = v.findViewById(R.id.read);
        mMeeting = v.findViewById(R.id.meeting);
        mGeneralMeeting = v.findViewById(R.id.general_meeting);
        mCommission = v.findViewById(R.id.commission);
        mLibrary = v.findViewById(R.id.library);
        mRead.setOnClickListener(this);
        mGeneralMeeting.setOnClickListener(this);
        mMeeting.setOnClickListener(this);
        mCommission.setOnClickListener(this);
        mLibrary.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.general_meeting:
                startActivity(new Intent(getContext() , GeneralMeetingActivity.class));
                break;
            case R.id.meeting:
                startActivity(new Intent(getContext() , MeetingActivity.class));
                break;
            case R.id.read:
                startActivity(new Intent(getContext() , PDFActivity.class));
                break;
            case R.id.commission:
                startActivity(new Intent(getContext() , ComissionActivity.class));
                break;
             case R.id.library:
                 String url="https://drive.google.com/drive/folders/0B39jsuKsL3G8TUFBd1NMRko2aTg?fbclid=IwAR1uGisekXc9fYx_IxB_-uk8pLFKNFG9pLLw9NlrxKrbNr_sq3IFXD13V2U";
                 Intent i = new Intent(Intent.ACTION_VIEW);
                 i.setData(Uri.parse(url));
                 startActivity(i);
                 break;
            default:
                break;
        }
    }

}
