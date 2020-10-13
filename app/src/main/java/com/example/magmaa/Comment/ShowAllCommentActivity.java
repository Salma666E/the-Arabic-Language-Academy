package com.example.magmaa.Comment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.example.magmaa.API_Mange.ApiService;
import com.example.magmaa.API_Mange.WebServiceClient;
import com.example.magmaa.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowAllCommentActivity extends AppCompatActivity {
    ArrayList<AllCommentResponse> mList;
    CommentAdapter mAdapter;
    RecyclerView mRecyclerView;
    List<String> mUserList;
    ArrayList<AllCommentResponse> mNewList;    //to fill list of comment
    SkeletonScreen skeletonScreen ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_comment);
        init();
    }

    private void init() {
        mRecyclerView = findViewById(R.id.comment_rv);
        mList = new ArrayList<>();
        mUserList = new ArrayList<>();
        mAdapter = new CommentAdapter(mList, this);
        callGetAllComment();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 1);
        mAdapter = new CommentAdapter(new ArrayList<AllCommentResponse>(), ShowAllCommentActivity.this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        skeletonScreen = Skeleton.bind(mRecyclerView)
                .adapter(mAdapter)
                .load(R.layout.item_skeleton_news)
                .show();
    }
    // all comment for this user
    private void callGetAllComment() {
        ApiService apiService = WebServiceClient.getRetrofit().create(ApiService.class);
        Call<List<AllCommentResponse>> call = apiService.getAllComment();

        call.enqueue(new Callback<List<AllCommentResponse>>() {
            @Override
            public void onResponse(Call<List<AllCommentResponse>> call, Response<List<AllCommentResponse>> response) {
                String userId =getIntent().getStringExtra("userId");
                mList = (ArrayList<AllCommentResponse>) response.body();
                mNewList =new ArrayList<>();
                for(int i=0 ;i<mList.size() ; i++){
                    if(mList.get(i).getUserId().toString().equals(userId))
                        mNewList.add(mList.get(i));
                }
                mAdapter.setmList(mNewList);
                skeletonScreen.hide();
            }
            @Override
            public void onFailure(Call<List<AllCommentResponse>>call, Throwable t) {
            }
        });
    }
}
