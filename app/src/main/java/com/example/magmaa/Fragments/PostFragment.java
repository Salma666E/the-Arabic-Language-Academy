package com.example.magmaa.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.example.magmaa.API_Mange.ApiService;
import com.example.magmaa.API_Mange.WebServiceClient;
import com.example.magmaa.R;
import com.example.magmaa.pages.ShowPost.PostAdapter;
import com.example.magmaa.pages.ShowPost.PostCatResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment {
    View view;
    RecyclerView mShowPostRv;
    ArrayList<PostCatResponse> mList;
    PostAdapter mAdapter;
    SkeletonScreen skeletonScreen ;
    public PostFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_post, container, false);
        initPost();
        return  view;
    }
    private void initPost() {
        mShowPostRv =view.findViewById(R.id.postsh_rv);
        mList = new ArrayList<>();
        mAdapter = new PostAdapter(mList, getActivity());
        callGetAllCatApi();
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        mAdapter = new PostAdapter(new ArrayList<PostCatResponse>(), getActivity());
        mShowPostRv.setAdapter(mAdapter);
        mShowPostRv.setLayoutManager(layoutManager);
        skeletonScreen = Skeleton.bind(mShowPostRv)
                .adapter(mAdapter)
                .load(R.layout.item_skeleton_news)
                .show();

    }
    private void callGetAllCatApi() {
        ApiService apiService = WebServiceClient.getRetrofit().create(ApiService.class);
        Call<List<PostCatResponse>> call=apiService.getAllPost();
        call.enqueue(new Callback<List<PostCatResponse>>() {
            @Override
            public void onResponse(Call<List<PostCatResponse>> call, Response<List<PostCatResponse>> response) {
                mList = (ArrayList<PostCatResponse>) response.body();
                mAdapter.setmList(mList);
                skeletonScreen.hide();
            }
            @Override
            public void onFailure(Call<List<PostCatResponse>> call, Throwable t) {
                Toast.makeText(getActivity(), "يوجد خطأ بإرجاع البيانات ", Toast.LENGTH_LONG).show();
            }
        });
    }
}
