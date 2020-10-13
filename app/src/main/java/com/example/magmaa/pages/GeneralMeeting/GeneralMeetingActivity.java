package com.example.magmaa.pages.GeneralMeeting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.example.magmaa.API_Mange.ApiService;
import com.example.magmaa.API_Mange.WebServiceClient;
import com.example.magmaa.R;
import com.example.magmaa.pages.MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GeneralMeetingActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ImageView mBack;
    ArrayList<GMeetingCatResponse> mList;
    GMeetingAdapter mAdapter;
    SkeletonScreen skeletonScreen ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_meeting);
        initViews();
    }
    private void initViews() {
        mBack = findViewById(R.id.back);
        mRecyclerView = findViewById(R.id.general_meeting_rv);
        mList = new ArrayList<>();
        mAdapter = new GMeetingAdapter(mList, this);
        callGetAllCatApi();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        mAdapter = new GMeetingAdapter(new ArrayList<GMeetingCatResponse>(), GeneralMeetingActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        skeletonScreen = Skeleton.bind(mRecyclerView)
                .adapter(mAdapter)
                .load(R.layout.item_skeleton_news)
                .show();
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GeneralMeetingActivity.this, MainActivity.class));
            }
        });
    }
    private void callGetAllCatApi() {
        ApiService apiService = WebServiceClient.getRetrofit().create(ApiService.class);
        Call<List<GMeetingCatResponse>> call = apiService.getAllGMeeting();
        call.enqueue(new Callback<List<GMeetingCatResponse>>() {
            @Override
            public void onResponse(Call<List<GMeetingCatResponse>> call, Response<List<GMeetingCatResponse>> response) {
                mList = (ArrayList<GMeetingCatResponse>) response.body();
                mAdapter.setmList(mList);
                skeletonScreen.hide();
            }
            @Override
            public void onFailure(Call<List<GMeetingCatResponse>> call, Throwable t) {
            }
        });
    }

}