package com.example.magmaa.pages.ShowPost;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.magmaa.API_Mange.WebServiceClient;
import com.example.magmaa.R;
import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.MyViewHolder> {
    WebServiceClient mWeb ;
    ArrayList<PostCatResponse> mList =new ArrayList<>();
    Context mContext;

    public PostAdapter(ArrayList<PostCatResponse> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }
    public void setmList(ArrayList<PostCatResponse> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_cat_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        //Call to API
            holder.mTextPost.setText(mList.get(position).getActivity());
        Glide.with(mContext)
                .load(mWeb.PHOTO_URL + mList.get(position).getImage())
                .placeholder(R.drawable.avatar)
                .into(holder.mImagePost);
    }
    @Override
    public int getItemCount() {
        if (mList==null)return 0;
        return mList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mTextPost;
        ImageView mImagePost;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextPost = itemView.findViewById(R.id.text_post);
            mImagePost = itemView.findViewById(R.id.image_post);
        }
    }
}