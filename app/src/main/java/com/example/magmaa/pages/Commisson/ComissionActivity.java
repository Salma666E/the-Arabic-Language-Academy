package com.example.magmaa.pages.Commisson;

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

public class ComissionActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ImageView mBack;
    ArrayList<CommissionCatResponse> mList;
    CommissionAdapter mAdapter;
    SkeletonScreen skeletonScreen ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comission);
        initViews();
    }
    private void initViews() {
        mBack = findViewById(R.id.back);
        mRecyclerView = findViewById(R.id.commission_rv);
        mList = new ArrayList<>();
        mAdapter = new CommissionAdapter(mList, this);
        callGetAllCatApi();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        mAdapter = new CommissionAdapter(new ArrayList<CommissionCatResponse>(), ComissionActivity.this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(layoutManager);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ComissionActivity.this, MainActivity.class));
            }
        });
        skeletonScreen = Skeleton.bind(mRecyclerView)
                .adapter(mAdapter)
                .load(R.layout.item_skeleton_news)
                .show();
    }
    private void callGetAllCatApi() {
        ApiService apiService = WebServiceClient.getRetrofit().create(ApiService.class);
        Call<List<CommissionCatResponse>> call = apiService.getAllCom();
        call.enqueue(new Callback<List<CommissionCatResponse>>() {
            @Override
            public void onResponse(Call<List<CommissionCatResponse>> call, Response<List<CommissionCatResponse>> response) {
                mList = (ArrayList<CommissionCatResponse>) response.body();
                mAdapter.setmList(mList);
                skeletonScreen.hide();
            }
            @Override
            public void onFailure(Call<List<CommissionCatResponse>> call, Throwable t) {
            }
        });

    }
}
