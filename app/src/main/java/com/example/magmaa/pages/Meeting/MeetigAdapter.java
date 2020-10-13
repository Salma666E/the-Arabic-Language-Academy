package com.example.magmaa.pages.Meeting;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.magmaa.API_Mange.ApiService;
import com.example.magmaa.API_Mange.WebServiceClient;
import com.example.magmaa.R;
import com.example.magmaa.pages.Commisson.CommissionCatResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeetigAdapter extends RecyclerView.Adapter<MeetigAdapter.MyViewHolder> {

    ArrayList<MeetingCatResponse> mList=new ArrayList<>();
    Context mContext;
    List<String> comm = new ArrayList<>();
    List<String> mcomN= new ArrayList<>();
    ArrayList<CommissionCatResponse> mcomList;

    public MeetigAdapter(ArrayList<MeetingCatResponse> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
        callGetComm();
    }

    public void setmList(ArrayList<MeetingCatResponse> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_cat_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        //Call to API
            holder.mMeetDate.setText(" والذى يقام بتاريخ : "+mList.get(position).getDate());
            holder.mMeetCourse.setText(" رقم المقرر : "+mList.get(position).getCourseNum());
            holder.mMeetSession.setText(" رقم الجلسة : "+mList.get(position).getSessionNum());
            for(int i=0;i<comm.size();i++)
                if(mList.get(position).getCommissionId().equals(comm.get(i)))
                {
                    holder.mcommId.setText("وهذا الاجتماع عقد للجنة : "+mcomN.get(i));
                    break;
                }
    }
    @Override
    public int getItemCount() {
        if (mList==null)return 0;
        return mList.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mMeetDate , mMeetCourse , mMeetSession , mcommId;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mMeetDate = itemView.findViewById(R.id.date_m);
            mMeetCourse = itemView.findViewById(R.id.course_num_m);
            mMeetSession = itemView.findViewById(R.id.session_num_m);
            mcommId = itemView.findViewById(R.id.comm);
        }
    }
    private void callGetComm() {
        mcomList = new ArrayList<>();

        ApiService apiService = WebServiceClient.getRetrofit().create(ApiService.class);
        Call<List<CommissionCatResponse>> call = apiService.getAllCom();
        call.enqueue(new Callback<List<CommissionCatResponse>>() {
            @Override
            public void onResponse(Call<List<CommissionCatResponse>> call, Response<List<CommissionCatResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Log.i("onSuccess", response.body().toString());
                        mcomList = (ArrayList<CommissionCatResponse>) response.body();
                        for (int i = 0; i < mcomList.size(); i++){
                            comm.add(mcomList.get(i).getId().toString());
                            mcomN.add(mcomList.get(i).getName());
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<List<CommissionCatResponse>> call, Throwable t) {
            }
        });

    }
}

