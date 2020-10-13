package com.example.magmaa.pages.GeneralMeeting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.magmaa.R;

import java.util.ArrayList;

public class GMeetingAdapter extends RecyclerView.Adapter<GMeetingAdapter.MyViewHolder> {

    ArrayList<GMeetingCatResponse> mList =new ArrayList<>();
    Context mContext;

    public GMeetingAdapter(ArrayList<GMeetingCatResponse> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }
    public void setmList(ArrayList<GMeetingCatResponse> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.gen_meeting_cat_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        //Call to API
            holder.mGM_Date.setText(" والذى يقام بتاريخ : "+mList.get(position).getDate());
            holder.mGM_Course.setText(" رقم المقرر : "+mList.get(position).getCourseNum());
            holder.mGM_session.setText(" رقم الجلسة : "+mList.get(position).getSessionNum());
    }

    @Override
    public int getItemCount() {
        if (mList==null)return 0;
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mGM_Date , mGM_Course , mGM_session;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mGM_Date = itemView.findViewById(R.id.date_gm);
            mGM_Course = itemView.findViewById(R.id.course_num_gm);
            mGM_session = itemView.findViewById(R.id.session_num_gm);

        }
    }
}

