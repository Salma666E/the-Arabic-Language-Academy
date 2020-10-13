package com.example.magmaa.pages.Commisson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.magmaa.R;

import java.util.ArrayList;

public class CommissionAdapter extends RecyclerView.Adapter<CommissionAdapter.MyViewHolder> {

    ArrayList<CommissionCatResponse> mList =new ArrayList<>();
    Context mContext;

    public CommissionAdapter(ArrayList<CommissionCatResponse> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }
    public void setmList(ArrayList<CommissionCatResponse> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.commission_cat_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        //Call to API
        holder.mComName.setText(" اللجنة : "+mList.get(position).getName());
        holder.mComDate.setText(" أنشئت عام "+mList.get(position).getYear());
        holder.mComDescrp.setText("  "+mList.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        if (mList==null)return 0;
        return mList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mComName , mComDate , mComDescrp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mComName = itemView.findViewById(R.id.commission_namme);
            mComDate = itemView.findViewById(R.id.commission_date);
            mComDescrp = itemView.findViewById(R.id.commiission_descrp);

        }
    }
}
