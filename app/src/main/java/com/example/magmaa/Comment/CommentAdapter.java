package com.example.magmaa.Comment;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.magmaa.API_Mange.ApiService;
import com.example.magmaa.API_Mange.SessionMangment;
import com.example.magmaa.API_Mange.WebServiceClient;
import com.example.magmaa.R;
import com.example.magmaa.UserResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyViewHolder> {

    ArrayList<UserResponse> muserList;
    SessionMangment mSH;
    String userId="";
    ArrayList<AllCommentResponse> mList=new ArrayList<>();
        Context mContext;

    public CommentAdapter(ArrayList<AllCommentResponse> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        muserList=new ArrayList<>();
        mSH = new SessionMangment(mContext);
        getUserId();
    }

    public void setmList(ArrayList<AllCommentResponse> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.comments_cat_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        //Call to API
        for (int p = 0; p < mList.size(); p++)
            if(userId.equals(mList.get(position).getUserId().toString())) {
                holder.id_arword.setText(" للكلمة العربية رقم : " + mList.get(position).getArabicWordId());
                holder.id_csword.setText(" للكلمة الانجليزية رقم : " + mList.get(position).getWordId());
                holder.show_com.setText(mList.get(position).getComment());
            }
        for (int u = 0; u < mList.size(); u++) {
            if (holder.id_arword.getText().toString().contains("null")) {
                holder.id_arword.setVisibility(View.GONE);
                holder.id_csword.setVisibility(View.VISIBLE);
            } else {
                holder.id_csword.setVisibility(View.GONE);
                holder.id_arword.setVisibility(View.VISIBLE);
            }
        }
        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeleteApiComment(mList.get(position).getId());
        }
        });
        holder.mUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(mContext, UpdateCommentActivity.class);
                i.putExtra("userId",userId);
                //i.putExtra("idWords",ShowAllCommentActivity.idWords.toString());
                i.putExtra("idCom",mList.get(position).getId().toString());
                i.putExtra("commentMsg",holder.show_com.getText().toString());
                if (holder.id_arword.getText().toString().contains("null")) {
                    i.putExtra("idCsWord", mList.get(position).getWordId().toString());
                    i.putExtra("idArWord", "");
                }
                else {
                    i.putExtra("idArWord", mList.get(position).getArabicWordId());
                    i.putExtra("idCsWord", "");
                }
                mContext.startActivity(i);
            }
        });
            }

    @Override
    public int getItemCount() {
        if (mList==null)return 0;
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView show_com , id_arword ,id_csword;
        Button mDelete , mUpdate ;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id_arword = itemView.findViewById(R.id.id_arword);
            id_csword = itemView.findViewById(R.id.id_csword);
            show_com = itemView.findViewById(R.id.show_com);
            mDelete = itemView.findViewById(R.id.delete);
            mUpdate = itemView.findViewById(R.id.update);
        }
    }

    //tp delete comment for this user
    private void getDeleteApiComment(int com){
        ApiService apiService = WebServiceClient.getRetrofit().create(ApiService.class);
        Call<Void> call = apiService.deleteComment(com);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Toast.makeText(mContext, "تم مسح التعليق", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(mContext, "فشل مسح التعليق", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // to get id for this user
    private void getUserId() {
        ApiService apiService = WebServiceClient.getRetrofit().create(ApiService.class);
        Call<List<UserResponse>> call = apiService.getAllUser();
        call.enqueue(new Callback<List<UserResponse>>() {
            @Override
            public void onResponse(Call<List<UserResponse>> call, Response<List<UserResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        muserList = (ArrayList<UserResponse>) response.body();
                        for (int i = 0; i < muserList.size(); i++) {
                            if(muserList.get(i).getEmail().equals(mSH.getUserDetails().get(mSH.KEY_EMAIL))) {
                                userId = muserList.get(i).getId().toString();
                            }
                        }
                    }
                }
                Log.e(" AllCommentResponse ", "correct return data: " + response.body());
            }
            @Override
            public void onFailure(Call<List<UserResponse>>call, Throwable t) {
                Log.e("error_connection  ", "  onFailure  : " + t.getMessage()+"->"+t.getCause()+" Call"+call.toString());
            }
        });
    }
    }


