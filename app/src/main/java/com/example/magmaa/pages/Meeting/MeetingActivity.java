package com.example.magmaa.pages.Meeting;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class MeetingActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ImageView mBack;
    ArrayList<MeetingCatResponse> mList;
    MeetigAdapter mAdapter;
    SkeletonScreen skeletonScreen ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting);
        initViews();
    }
    private void initViews() {
        mBack = findViewById(R.id.back);
        mRecyclerView = findViewById(R.id.meeting_rv);
        mList = new ArrayList<>();
        mAdapter = new MeetigAdapter(mList, this);
        callGetAllCatApi();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        mAdapter = new MeetigAdapter(new ArrayList<MeetingCatResponse>(), MeetingActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        skeletonScreen = Skeleton.bind(mRecyclerView)
                .adapter(mAdapter)
                .load(R.layout.item_skeleton_news)
                .show();
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MeetingActivity.this, MainActivity.class));
            }
        });
    }
    private void callGetAllCatApi() {
        ApiService apiService =  WebServiceClient.getRetrofit().create(ApiService.class);
        Call<List<MeetingCatResponse>> call = apiService.getAllMeeting();
        call.enqueue(new Callback<List<MeetingCatResponse>>() {
            @Override
            public void onResponse(Call<List<MeetingCatResponse>> call, Response<List<MeetingCatResponse>> response) {
                mList = (ArrayList<MeetingCatResponse>) response.body();
                mAdapter.setmList(mList);
                skeletonScreen.hide();
                Log.e("MeetingActivity Respon", "correct return data: " + response.body());
            }

            @Override
            public void onFailure(Call<List<MeetingCatResponse>>call, Throwable t) {
                Log.e("error_connection  ", "  onFailure  : " + t.getMessage()+"->"+t.getCause()+" Call"+call.toString());
            }
        });
    }


}
